package jp.kaoru.picasaapi;

/**
 * 17/11/06にかおるが作成しました
 */

public class Entry {

        Long id;
        String Published;
        String Updated;
        String Title;
        String Content;
        String Thumbnail;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPublished() {
            return Published;
        }

        public void setPublished(String date) {
            this.Published = date;
        }

        public String getUpdated() {
            return Updated;
        }

        public void setUpdated(String date) {
            this.Updated = date;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getThumbnail() {
            return Thumbnail;
        }

        public void setThumbnail(String Thumbnail) {
            this.Thumbnail = Thumbnail;
        }

}
