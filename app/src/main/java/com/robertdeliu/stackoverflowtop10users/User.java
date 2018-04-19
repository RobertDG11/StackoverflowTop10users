package com.robertdeliu.stackoverflowtop10users;

public class User {
    private String profileImage;
    private String name;
    private int positionInTop;
    private String reputation;
    private String location;
    private String[] badges;

    static class Builder {
        User user = new User();

        Builder withName(String name) {
            user.setName(name);
            return this;
        }

        Builder withProfileImage(String profileImage) {
            user.setProfileImage(profileImage);
            return this;
        }

        Builder withPositionInTop(int positionInTop) {
            user.setPositionInTop(positionInTop);
            return this;
        }

        Builder withReputation(String reputation) {
            user.setReputation(reputation);
            return this;
        }

        Builder withLocation(String location) {
            user.setLocation(location);
            return this;
        }

        Builder withBadges(String[] badges) {
            user.setBadges(badges);
            return this;
        }

        User build() {
            return user;
        }
    }

    String getProfileImage() {
        return profileImage;
    }

    private void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    int getPositionInTop() {
        return positionInTop;
    }

    private void setPositionInTop(int positionInTop) {
        this.positionInTop = positionInTop;
    }

    String getReputation() {
        return reputation;
    }

    private void setReputation(String reputation) {
        this.reputation = reputation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String[] getBadges() {
        return badges;
    }

    public void setBadges(String[] badges) {
        this.badges = badges;
    }
}
