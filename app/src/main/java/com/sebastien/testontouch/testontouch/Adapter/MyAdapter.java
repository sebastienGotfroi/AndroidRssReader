package com.sebastien.testontouch.testontouch.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.URLLoader;
import com.sebastien.testontouch.testontouch.bean.Article;
import com.sebastien.testontouch.testontouch.service.impl.RssServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Sébastien on 30-03-16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Set<Article> listArticles;
    private URLLoader urlLoader;

    public MyAdapter(URLLoader urlLoader){
        super();

        this.urlLoader = urlLoader;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.article_cell, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        Article article =(Article) listArticles.toArray()[i];
        viewHolder.display(article);
    }

    @Override
    public int getItemCount() {
        return listArticles ==null ? 0 :listArticles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView articleTitle;

        private Article currentArticle;

        private ImageView iconFavorite;

        public MyViewHolder (final View itemView) {
            super(itemView);

            articleTitle = ((TextView) itemView.findViewById(R.id.articleTitle));

            iconFavorite = ((ImageView) itemView.findViewById(R.id.iconFavorite));

            iconFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentArticle.getFavorite()){
                        iconFavorite.setImageDrawable(v.getResources().getDrawable(android.R.drawable.btn_star_big_off, null));
                        currentArticle.setFavorite(false);
                        RssServiceImpl.getRssService().deleteFavorite(itemView.getContext(), currentArticle);
                    }
                    else{
                        iconFavorite.setImageDrawable(v.getResources().getDrawable(android.R.drawable.btn_star_big_on,null));
                        currentArticle.setFavorite(true);
                        RssServiceImpl.getRssService().addFavorite(currentArticle, itemView.getContext());
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    urlLoader.load(currentArticle.getTitle(), currentArticle.getArticleLink() );
                }
            });
        }

        public void display (Article article){
            currentArticle = article;

            if(currentArticle.getFavorite()){
                iconFavorite.setImageDrawable(itemView.getResources().getDrawable(android.R.drawable.btn_star_big_on, null));
            }
            else{
                iconFavorite.setImageDrawable(itemView.getResources().getDrawable(android.R.drawable.btn_star_big_off,null));
            }

            articleTitle.setText(article.getTitle());
        }
    }

    public void clearList(){
        if(listArticles!= null) {
            listArticles.clear();
            notifyDataSetChanged();
        }
    }

    public void setListArticles(Set<Article> listArticles){
        this.listArticles = listArticles;
        notifyDataSetChanged();
    }

    public void addListArticles (List<Article> listArticles) {

        if(this.listArticles == null)
            this.listArticles = new TreeSet<Article>();
        this.listArticles.addAll(listArticles);
        notifyDataSetChanged();
    }
}
