import React from "react";
import { useTitle } from "../../hooks/useTitle";

function ProfilePage() {
    useTitle("profile");

    return (
        <div>Profile</div>
    );
}

export default ProfilePage;