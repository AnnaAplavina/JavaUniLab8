package data.database.users;

public class User {
    private String username;
    private String encryptedPass;

    public User(String username, String encryptedPass){
        this.username = username;
        this.encryptedPass = encryptedPass;
    }

    public String getUsername(){
        return username;
    }

    public String getEncryptedPass(){
        return encryptedPass;
    }

    @Override
    public boolean equals(Object object){
        if(object == null){
            return false;
        }
        if(!(object instanceof User)){
            return false;
        }
        User otherUser = (User)object;
        return username.equals(otherUser.getUsername()) && encryptedPass.equals(otherUser.encryptedPass);
    }
}
