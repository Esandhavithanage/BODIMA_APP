package com.example.hideoutcabins.pojo;

public class Traveler {

    String  fname;
    String  lname;
    String  pnum;
    String  email;
    String  nic;
    String  password;
    String  cpassword;

    public Traveler() {
    }

    public Traveler(String fname, String lname, String pnum, String email, String nic, String password, String cpassword) {
        this.fname = fname;
        this.lname = lname;
        this.pnum = pnum;
        this.email = email;
        this.nic = nic;
        this.password = password;
        this.cpassword = cpassword;
    }

    public String getFname() {return fname;}
    public void setFname(String fname) {this.fname = fname;}

    public String getLname() {return lname;}
    public void setLname(String lname) {this.lname = lname;}

    public String getPnum() {return pnum;}
    public void setPnum(String pnum) { this.pnum = pnum;}

    public String getEmail() {return email;}
    public void setEmail(String email) { this.email = email;}

    public String getNic() {return nic;}
    public void setNic(String nic) {this.nic = nic;}

    public String getPassword() {return password;}
    public void setPassword(String password){ this.password = password;}


    public String getCpassword(){return cpassword;}
    public void setCpassword(String Cpassword) {this.cpassword = Cpassword;}

    @Override
    public String toString() {
        return "Traveler{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", pnum='" + pnum + '\'' +
                ", email='" + email + '\'' +
                ", nic='" + nic + '\'' +
                ", password='" + password + '\'' +
                ", cpassword='" + cpassword + '\'' +
                '}';
    }
}
