package com.sebastien.testontouch.testontouch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sebastien.testontouch.R;
import com.sebastien.testontouch.testontouch.bean.Article;

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

        public MyViewHolder (final View itemView) {
            super(itemView);

            articleTitle = ((TextView) itemView.findViewById(R.id.articleTitle));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    urlLoader.load(currentArticle.getTitle(), currentArticle.getArticleLink() );
                }
            });
        }

        public void display (Article article){
            currentArticle = article;
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
    }

    public void addListArticles (List<Article> listArticles) {

        if(this.listArticles == null)
            this.listArticles = new TreeSet<Article>();
        this.listArticles.addAll(listArticles);
        notifyDataSetChanged();
    }
}
