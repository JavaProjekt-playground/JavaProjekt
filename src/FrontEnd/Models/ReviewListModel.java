package FrontEnd.Models;

import Database.Review;

import javax.swing.*;
import java.util.Vector;

public class ReviewListModel extends DefaultListModel<Review> {

    public ReviewListModel(Vector<Review> items){
        for(Review i : items) addElement(i);
    }
}
