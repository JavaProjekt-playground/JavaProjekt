package FrontEnd.Models;

import Database.Review;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class ReviewsTableModel {
    public class reviewsTableModel extends DefaultTableModel {
        private String[] columnNames = new String[]{
                "Message", "Score"
        };
        private Object[][] data = {
                {new Object(), new Object()}
        };
        private Review[] reviews;

        public reviewsTableModel(Vector<Review> r) {
            super();

            reviews = new Review[r.size()];
            reviews = r.toArray(reviews);
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return reviews != null ? reviews.length : 0;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int row, int column) {
            switch (column) {
                case 0:
                    return reviews[row].Message;
                case 1:
                    return reviews[row].Score;
                default:
                    return null;
            }
        }

    }
}
