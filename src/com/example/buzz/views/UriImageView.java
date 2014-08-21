package com.example.buzz.views;

import android.content.Context;
import android.widget.ImageView;
import android.net.Uri;
import java.net.URI;

// Adds support for java.net.Uri to ImageView
public class UriImageView extends ImageView {
  public UriImageView(Context context) {
    super(context);
  }

  public void setImageUri(URI uri) {
    setImageUri(uri.toString());
  }
  public void setImageUri(String uri) {
    super.setImageURI(Uri.parse(uri));
  }
}
