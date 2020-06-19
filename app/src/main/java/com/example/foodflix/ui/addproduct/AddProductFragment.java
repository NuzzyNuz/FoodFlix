/*******************************************************************************
 * Copyright (c) 2020. All Rights Reserved by Nuzrah Nilamdeen
 ******************************************************************************/

package com.example.foodflix.ui.addproduct;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import com.example.foodflix.R;
import com.example.foodflix.helpers.constant.AppConstants;
import com.example.foodflix.helpers.constant.IntentKey;
import com.example.foodflix.helpers.constant.PreferenceKey;
import com.example.foodflix.helpers.model.Code;
import com.example.foodflix.helpers.util.FileUtil;
import com.example.foodflix.helpers.util.ProgressDialogUtil;
import com.example.foodflix.helpers.util.SharedPrefUtil;
import com.example.foodflix.helpers.util.image.ImageInfo;
import com.example.foodflix.helpers.util.image.ImagePicker;
import com.example.foodflix.ui.pickedfromgallery.PickedFromGalleryActivity;
import com.example.foodflix.ui.viewproduct.ViewProductActivity;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.common.HybridBinarizer;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;


/**
 * The type Add product fragment.
 */
public class AddProductFragment extends androidx.fragment.app.Fragment implements View.OnClickListener {

    private Context mContext;
    private Activity mActivity;
    private DecoratedBarcodeView mBarcodeView;
    private BeepManager mBeepManager;
    private TextView mTextViewFlash, mTextViewScanGallery, buttonGoToProduct, buttonViewProduct;
    private boolean mIsFlashOn;

    /**
     * Instantiates a new Add product fragment.
     */
    public AddProductFragment() {

    }

    /**
     * New instance com . example . foodflix . ui . addproduct . add product fragment.
     *
     * @return the com . example . foodflix . ui . addproduct . add product fragment
     */
    public static com.example.foodflix.ui.addproduct.AddProductFragment newInstance() {
        return new com.example.foodflix.ui.addproduct.AddProductFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() == null) {
            return;
        } else {
            mActivity = getActivity();
        }

        initializeViews(view);
        doPreRequisites();
        setListeners();
        doScan();
    }

    private void doScan() {
        mBarcodeView.decodeSingle(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                mBarcodeView.pause();
                mBeepManager.playBeepSoundAndVibrate();

                if (result != null
                        && !TextUtils.isEmpty(result.getText())
                        && !TextUtils.isEmpty(result.getBarcodeFormat().name())) {

                    Code code;

                    if (result.getBitmap() != null) {
                        int typeIndex = result.getBarcodeFormat().name().toLowerCase().startsWith("qr")
                                ? Code.QR_CODE : Code.BAR_CODE;
                        String type = getResources().getStringArray(R.array.code_types)[typeIndex];

                        @SuppressLint("StringFormatMatches") File codeImageFile = FileUtil.getEmptyFile(mContext, AppConstants.PREFIX_IMAGE,
                                String.format(Locale.ENGLISH, getString(R.string.file_name_body),
                                        type.substring(0, type.indexOf(" Code")),
                                        System.currentTimeMillis()),
                                AppConstants.SUFFIX_IMAGE,
                                Environment.DIRECTORY_PICTURES);

                        if (codeImageFile != null) {
                            try (FileOutputStream out = new FileOutputStream(codeImageFile)) {
                                result.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);

                                code = new Code(result.getText(),
                                        result.getBarcodeFormat().name().toLowerCase().startsWith("qr")
                                                ? Code.QR_CODE : Code.BAR_CODE,
                                        codeImageFile.getPath(), result.getResult().getTimestamp());
                            } catch (IOException e) {
                                if (!TextUtils.isEmpty(e.getMessage())) {
                                    Log.e(getClass().getSimpleName(), e.getMessage());
                                }

                                code = new Code(result.getText(),
                                        result.getBarcodeFormat().name().toLowerCase().startsWith("qr")
                                                ? Code.QR_CODE : Code.BAR_CODE, result.getResult().getTimestamp());
                            }
                        } else {
                            code = new Code(result.getText(),
                                    result.getBarcodeFormat().name().toLowerCase().startsWith("qr")
                                            ? Code.QR_CODE : Code.BAR_CODE, result.getResult().getTimestamp());
                        }
                    } else {
                        code = new Code(result.getText(),
                                result.getBarcodeFormat().name().toLowerCase().startsWith("qr")
                                        ? Code.QR_CODE : Code.BAR_CODE, result.getResult().getTimestamp());
                    }

                    Intent intent = new Intent(mContext, ScanProductActivity.class);
                    intent.putExtra(IntentKey.MODEL, code);
                    startActivity(intent);
                } else {
                    mBarcodeView.resume();
                    doScan();
                    Toast.makeText(mContext, getString(R.string.error_occured_while_scanning),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    private void doPreRequisites() {
        mBeepManager = new BeepManager(mActivity);
        mBeepManager.setVibrateEnabled(SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.VIBRATE));
        mBeepManager.setBeepEnabled(SharedPrefUtil.readBooleanDefaultTrue(PreferenceKey.PLAY_SOUND));
        mBarcodeView.setStatusText(AppConstants.EMPTY_STRING);
    }

    /**
     * @param view
     */
    private void initializeViews(@NonNull View view) {
        mTextViewFlash = view.findViewById(R.id.text_view_set_flash);
        mTextViewScanGallery = view.findViewById(R.id.text_view_scan_gallery);
        mBarcodeView = view.findViewById(R.id.barcode_view);
        buttonGoToProduct = view.findViewById(R.id.buttonGoToProduct);
        buttonViewProduct = view.findViewById(R.id.buttonGoToViewProduct);
    }

    private void setListeners() {
        mTextViewFlash.setOnClickListener(this);
        mTextViewScanGallery.setOnClickListener(this);
        buttonGoToProduct.setOnClickListener(this);
        buttonViewProduct.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        doPreRequisites();
        mBarcodeView.resume();
        doScan();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBarcodeView.pause();
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_view_set_flash:
                if (mContext == null) return;

                Drawable flashIcon = ContextCompat.getDrawable(mContext,
                        mIsFlashOn ? R.drawable.ic_flash_off : R.drawable.ic_flash_on);
                mTextViewFlash.setCompoundDrawablesWithIntrinsicBounds(null, flashIcon, null, null);

                if (mIsFlashOn) {
                    mBarcodeView.setTorchOff();
                } else {
                    mBarcodeView.setTorchOn();
                }

                mIsFlashOn = !mIsFlashOn;
                break;

            case R.id.text_view_scan_gallery:
                ImagePicker.pickImage(this);
                break;

            case R.id.buttonGoToProduct:
                Intent intent = new Intent(getActivity(), ScanProductActivity.class);
                startActivity(intent);
                break;

            case R.id.buttonGoToViewProduct:
                Intent intent2 = new Intent(getActivity(), ViewProductActivity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImagePicker.REQUEST_CODE_PICK_IMAGE && mContext != null) {
            ProgressDialogUtil.on().showProgressDialog(mContext);
            Bitmap bitmap = ImagePicker.getPickedImageFromResult(mContext, resultCode, data);
            Result result = processBitmapToGetResult(bitmap);

            if (result != null) {
                ImageInfo imageInfo = ImagePicker.getPickedImageInfo(mContext, resultCode, data);

                if (imageInfo != null
                        && imageInfo.getImageUri() != null) {

                    String imagePath = getPathFromUri(imageInfo.getImageUri());

                    if (!TextUtils.isEmpty(imagePath)) {
                        int typeIndex = result.getBarcodeFormat().name().toLowerCase().startsWith("qr")
                                ? Code.QR_CODE : Code.BAR_CODE;

                        Code code = new Code(result.getText(), typeIndex,
                                imagePath, result.getTimestamp());

                        Intent intent = new Intent(mContext, PickedFromGalleryActivity.class);
                        intent.putExtra(IntentKey.MODEL, code);
                        startActivity(intent);
                    } else {
                        Toast.makeText(mContext, getString(R.string.error_did_not_find_any_content),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, getString(R.string.error_did_not_find_any_content),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * @param bitmap
     * @return Result
     */
    private Result processBitmapToGetResult(Bitmap bitmap) {
        if (bitmap != null) {
            int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
            bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
            LuminanceSource source =
                    new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            Reader reader = new MultiFormatReader();
            try {
                Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<>();
                decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

                Result result = reader.decode(binaryBitmap, decodeHints);
                String codeResult = result.getText();

                if (!TextUtils.isEmpty(codeResult)) {
                    ProgressDialogUtil.on().hideProgressDialog();
                    return result;
                } else {
                    ProgressDialogUtil.on().hideProgressDialog();
                    Toast.makeText(mContext, getString(R.string.error_did_not_find_any_content),
                            Toast.LENGTH_SHORT).show();

                    return null;
                }

            } catch (Exception e) {
                ProgressDialogUtil.on().hideProgressDialog();
                Toast.makeText(mContext, getString(R.string.error_did_not_find_any_content),
                        Toast.LENGTH_SHORT).show();

                if (!TextUtils.isEmpty(e.getMessage())) {
                    Log.d(getClass().getSimpleName(), e.getMessage());
                }

                return null;
            }
        } else {
            ProgressDialogUtil.on().hideProgressDialog();
            Toast.makeText(mContext, getString(R.string.error_could_not_load_the_image),
                    Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * @param uri
     * @return cursor
     */
    private String getPathFromUri(Uri uri) {
        if (mContext == null) {
            return null;
        }

        String[] data = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(mContext, uri, data, null, null, null);
        Cursor cursor = loader.loadInBackground();
        if (cursor == null) {
            return null;
        }

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
