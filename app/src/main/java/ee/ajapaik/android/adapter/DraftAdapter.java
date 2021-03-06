package ee.ajapaik.android.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ee.ajapaik.android.R;
import ee.ajapaik.android.data.Photo;
import ee.ajapaik.android.data.PhotoDraftsDTO;
import ee.ajapaik.android.util.Images;
import ee.ajapaik.android.util.Size;
import ee.ajapaik.android.util.Strings;
import ee.ajapaik.android.widget.StaggeredGridView;

public class DraftAdapter extends StaggeredGridView.Adapter {
    private static final int THUMBNAIL_SIZE = 200;

    private DraftAdapter.OnPhotoSelectionListener m_listener;
    private Location m_location;
    private List<PhotoDraftsDTO> m_photos;

    public DraftAdapter(Context context, List<PhotoDraftsDTO> photos, Location location, OnPhotoSelectionListener listener) {
        super(context);

        m_listener = listener;
        m_location = location;
        m_photos = photos;
    }

    @Override
    public View createItemView(Context context, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_photo_item, parent, false);
    }

    @Override
    public void bindItemView(int position, View view) {
        ImageView imageView = (ImageView)view.findViewById(R.id.image_background);
        TextView textView = (TextView)view.findViewById(R.id.text_distance);
        Button button = (Button)view.findViewById(R.id.button);
        PhotoDraftsDTO rephotoDraft = m_photos.get(position);
        final Photo photo = rephotoDraft.getPhoto();

        imageView.setImageURI(photo.getThumbnail(THUMBNAIL_SIZE));
        textView.setText(Strings.toLocalizedDistance(view.getContext(), photo.getLocation(), m_location));

        imageView = (ImageView)view.findViewById(R.id.image_rephoto);
        imageView.setImageResource(Images.toRephotoDraftCountDrawableId(rephotoDraft.getDraftCount()));

        if (photo.isFavorited()) {
            imageView = (ImageView) view.findViewById(R.id.image_favorited);
            imageView.setImageResource(R.drawable.ic_favorite_white_36dp);
            imageView.setColorFilter(view.getContext().getResources().getColor(R.color.tint), PorterDuff.Mode.MULTIPLY);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m_listener != null) {
                    m_listener.onSelect(photo);
                }
            }
        });
    }

    @Override
    public Size getSize(int position) {
        return m_photos.get(position).getPhoto().getSize();
    }

    @Override
    public int getCount() {
        return (m_photos != null) ? m_photos.size() : 0;
    }

    public interface OnPhotoSelectionListener {
        void onSelect(Photo photo);
    }
}
