package de.linkelisteortenau.app.backend.sql.articles

/**
 * @author Manuel Ziel
 * @since 0.0.1 Beta 2023-02
 *
 * Constants for the Article SQL-Database
 **/
import android.provider.BaseColumns

/**
 * Constants for the Article SQL-Database
 * @author Manuel Ziel
 *
 * ARTICLES_TABLE_NAME for the Article SQL-Table
 * ID for the Columns
 *
 * COLUMN_ARTICLE_TITLE is the Title of the Article.
 * COLUMN_ARTICLE_LINK is the href https link to the Website of the Article.
 * COLUMN_ARTICLE_CONTENT is the Text Content of the Article.
 * COLUMN_ARTICLE_FLAG is the Flag to check the Article whether this is still up to date.
 **/
const val ARTICLES_TABLE_NAME = "articles_table_lilo"
const val ARTICLE_ID = BaseColumns._ID

const val COLUMN_ARTICLE_TITLE = "title";         const val article_title_index: Int = 1
const val COLUMN_ARTICLE_LINK = "link";           const val article_link_index: Int = 2
const val COLUMN_ARTICLE_CONTENT = "content";     const val article_content_index: Int = 3
const val COLUMN_ARTICLE_FLAG = "flag";           const val article_flag_index: Int = 4

