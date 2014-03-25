package in.co.recex.dtutimes.app;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;

/**
 * Created by ashutosh on 21/3/14.
 */
public class GPlayGridCard extends Card {

    protected TextView mTitle;
    protected TextView mSecondaryTitle;
    protected int resourceIdThumbnail = -1;
    protected int count;

    protected String headerTitle;
    protected String secondaryTitle;
    protected float rating;

    public GPlayGridCard(Context context) {
        super(context, R.layout.carddemo_gplay_inner_content);
    }

    public GPlayGridCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    private void init() {
        CardHeader header = new CardHeader(getContext());
        header.setButtonOverflowVisible(true);
        header.setTitle(headerTitle);
        header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                Toast.makeText(getContext(), "Item " + item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        addCardHeader(header);

        GplayGridThumb thumbnail = new GplayGridThumb(getContext());
        if (resourceIdThumbnail > -1)
            thumbnail.setDrawableResource(resourceIdThumbnail);
        else
            thumbnail.setDrawableResource(R.drawable.ic_ic_launcher_web);
        addCardThumbnail(thumbnail);

        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                //Do something
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        TextView title = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_title);
        title.setText("FREE");

        TextView subtitle = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
        subtitle.setText(secondaryTitle);
    }

    class GplayGridThumb extends CardThumbnail {

        public GplayGridThumb(Context context) {
            super(context);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            //viewImage.getLayoutParams().width = 196;
            //viewImage.getLayoutParams().height = 196;

        }
    }

}
