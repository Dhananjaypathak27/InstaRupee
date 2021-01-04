package in.xparticle.instarupee.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Products implements Parcelable {

    String MRP,city,image,isAvailable,mobileNumber,
            numberOfMonths,productDescription,sellingPrice,state,title;

    public Products() {
    }

    public Products(String MRP, String city, String image, String isAvailable, String mobileNumber, String numberOfMonths,
                    String productDescription, String sellingPrice, String state, String title) {
        this.MRP = MRP;
        this.city = city;
        this.image = image;
        this.isAvailable = isAvailable;
        this.mobileNumber = mobileNumber;
        this.numberOfMonths = numberOfMonths;
        this.productDescription = productDescription;
        this.sellingPrice = sellingPrice;
        this.state = state;
        this.title = title;
    }

    protected Products(Parcel in) {
        MRP = in.readString();
        city = in.readString();
        image = in.readString();
        isAvailable = in.readString();
        mobileNumber = in.readString();
        numberOfMonths = in.readString();
        productDescription = in.readString();
        sellingPrice = in.readString();
        state = in.readString();
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(MRP);
        dest.writeString(city);
        dest.writeString(image);
        dest.writeString(isAvailable);
        dest.writeString(mobileNumber);
        dest.writeString(numberOfMonths);
        dest.writeString(productDescription);
        dest.writeString(sellingPrice);
        dest.writeString(state);
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    @Override
    public String toString() {
        return "Products{" +
                "MRP='" + MRP + '\'' +
                ", city='" + city + '\'' +
                ", image='" + image + '\'' +
                ", isAvailable='" + isAvailable + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", numberOfMonths='" + numberOfMonths + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", state='" + state + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNumberOfMonths() {
        return numberOfMonths;
    }

    public void setNumberOfMonths(String numberOfMonths) {
        this.numberOfMonths = numberOfMonths;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
