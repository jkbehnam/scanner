package com.patient.mokhtari.scanner.activities.New_request;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.patient.mokhtari.scanner.R;
import com.patient.mokhtari.scanner.activities.Adapters.adapterAddPhoto;
import com.patient.mokhtari.scanner.activities.CustomItems.RtlGridLayoutManager;
import com.patient.mokhtari.scanner.activities.CustomItems.myFragment;
import com.patient.mokhtari.scanner.activities.Frag_request_details;
import com.patient.mokhtari.scanner.activities.New_request.select_photo.ImagePickerActivity;
import com.patient.mokhtari.scanner.activities.Objects.AddImage;
import com.patient.mokhtari.scanner.activities.Objects.ReqPhoto;
import com.patient.mokhtari.scanner.activities.Objects.Request;
import com.patient.mokhtari.scanner.activities.camera_tips.camera_tips_main;
import com.patient.mokhtari.scanner.activities.webservice.ConnectToServer;
import com.patient.mokhtari.scanner.activities.webservice.VolleyCallback;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.patient.mokhtari.scanner.activities.Frag_new_request.reqBodyPhotosArrayList;
import static com.yalantis.ucrop.UCropFragment.TAG;


public class AddSkinPhoto extends myFragment implements View.OnClickListener {
    public static final int REQUEST_IMAGE = 100;
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.btn_camera_tips)
    CardView btn_camera_tips;
    @BindView(R.id.MainActivity_recycle)
    RecyclerView mainActivity_recycle;
    @BindView(R.id.btnSkin)
    CardView btnSkin;
    int position;
    ArrayList<AddImage> glist;
    adapterAddPhoto madapter;
    Request request;

    // TODO: Rename and change types and number of parameters
    public static AddSkinPhoto newInstance() {

        return new AddSkinPhoto();
    }

    public static AddSkinPhoto newInstance(Request request) {
        AddSkinPhoto fragment = new AddSkinPhoto();
        fragment.request = request;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_add_skin_photo, container, false);
        ButterKnife.bind(this, rootView);
        btnSkin.setOnClickListener(this);
        setFragmentActivity(getActivity());
        setToolbar_notmain(rootView, "ارسال تصویر ضایعه");
        RtlGridLayoutManager layoutManager = new RtlGridLayoutManager(getActivity(), 3);

        btn_camera_tips.setOnClickListener(this);
        mainActivity_recycle.setLayoutManager(layoutManager);
        glist = new ArrayList<>();
        glist.add(new AddImage(""));
        glist.add(new AddImage(""));
        glist.add(new AddImage(""));
        glist.add(new AddImage(""));
        glist.add(new AddImage(""));

            for (ReqPhoto req : reqBodyPhotosArrayList
            ) {
                glist.set(req.getId(),new AddImage(req.getUrl()));
            }

        madapter = new adapterAddPhoto(glist);
        mainActivity_recycle.setAdapter(madapter);

        madapter.setOnCardClickListner((view, position2) -> {
            position = position2;
            Dexter.withActivity(getActivity())
                    .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                showImagePickerOptions();
                            }

                            if (report.isAnyPermissionPermanentlyDenied()) {
                                showSettingsDialog();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();

        });


        return rootView;
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //  if (context instanceof OnFragmentInteractionListener) {
        //      mListener = (OnFragmentInteractionListener) context;
        //   } else {
        //     throw new RuntimeException(context.toString()
        //              + " must implement OnFragmentInteractionListener");
        //   }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_camera_tips:
                loadFragment(camera_tips_main.newInstance());

                break;
            case R.id.btnSkin:
                if (request != null) {
                    showLoading_base();
                    ConnectToServer.uploadBitmap(result -> {
                        hideLoading_base();
                        Frag_request_details.getit().getRequestDetail();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        fm.popBackStack();

                        // reciveRequest(result);
                    }, reqBodyPhotosArrayList, request, "body");
                } else {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                }
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        glist.get(position).setAddress(url);
        madapter.notifyDataSetChanged();
        madapter.notifyItemChanged(position);
        boolean dup = false;
        for (ReqPhoto req : reqBodyPhotosArrayList
        ) {
            if (req.getId() == position) {
                dup = true;
                reqBodyPhotosArrayList.set(reqBodyPhotosArrayList.indexOf(req), new ReqPhoto(position, url));
                Log.d("behnamBodyphoto", reqBodyPhotosArrayList.toString());
            }

        }
        if (!dup) {
            reqBodyPhotosArrayList.add(new ReqPhoto(position, url));
            Log.d("behnamBodyphoto", reqBodyPhotosArrayList.toString());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isDark(Bitmap bitmap) {
        boolean dark = false;

        float darkThreshold = bitmap.getWidth() * bitmap.getHeight() * 0.45f;
        int darkPixels = 0;

        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

        for (int pixel : pixels) {
            int r = Color.red(pixel);
            int g = Color.green(pixel);
            int b = Color.blue(pixel);
            double luminance = (0.299 * r + 0.0f + 0.587 * g + 0.0f + 0.114 * b + 0.0f);
            if (luminance < 150) {
                darkPixels++;
            }
        }

        if (darkPixels >= darkThreshold) {
            dark = true;
        }
        //  long duration = System.currentTimeMillis()-s;
        return dark;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
