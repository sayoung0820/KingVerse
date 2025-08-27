package com.example.kingverse.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;


@Table("book")
public class Book {
    @Id
    @Column("book_id")
    private Integer bookId;
    private String title;

    @Column("cover_url")
    private String coverUrl;

    @Column("year_published")
    private Integer yearPublished;

    private String setting;
    private String series;
    private String summary;

    public Book() {}

    public Book(Integer bookId, String title, Integer yearPublished, String setting, String series, String summary, String coverUrl) {
        this.bookId = bookId;
        this.title = title;
        this.yearPublished = yearPublished;
        this.setting = setting;
        this.series = series;
        this.summary = summary;
        this.coverUrl = coverUrl;
    }

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Integer getYearPublished() { return yearPublished; }
    public void setYearPublished(Integer yearPublished) { this.yearPublished = yearPublished; }

    public String getSetting() { return setting; }
    public void setSetting(String setting) { this.setting = setting; }

    public String getSeries() { return series; }
    public void setSeries(String series) { this.series = series; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }


    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", yearPublished=" + yearPublished +
                ", setting='" + setting + '\'' +
                ", series='" + series + '\'' +
                ", summary='" + summary + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                '}';
    }
}
