import React, { useEffect } from "react";
import { pages } from "../../constants/pages";
import { useLink } from "../../hooks/useLink";
import { useTitle } from "../../hooks/useTitle";
import Message from "../Message/Message";
import RoleController from "../RoleController/RoleController";
import { getUser } from "./../../handler/StorageHandler";
import UserDataForm from "./UserDataForm";
import M from "materialize-css";
import ProposedTopicsButton from "./ProposedTopicsButton";

function ProfilePage() {
    useTitle("profile");

    useEffect(() => {
        M.updateTextFields();
    });

    const user = getUser();

    return (
        <div className="container">
            <div className="row">
                <div className="col s12 my20">
                    <div className="s-vflex m-hflex">
                        <div className="equal-flex s-hflex-center m-hflex-end px10">
                            <div className="z-depth-1 user-profile-avatar stretch-background border50p" style={{
                                backgroundImage: `url('/shared/images/avatars/${user.imagePath}')`
                            }}></div>
                        </div>
                        <div className="equal-flex s-hflex-center m-hflex-start px10">
                            <div className="s-vflex-center">
                                <form action={useLink(pages.changeAvatar)} method="post" encType="multipart/form-data">
                                    <div className="file-field input-field">
                                        <div className="btn waves-effect waves-light">
                                            <span data-forward-click="user-avatar"><Message alias="change_photo" /></span>
                                            <input id="user-avatar" type="file" name="avatar" accept="image/*" className="hidden" />
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col s12">
                    <div className="s-hflex">
                        <div className="equal-flex">
                            <h4 className="grey-text text-darken-2 mb0"><Message alias="profile" /></h4>
                        </div>
                        <div className="s-vflex-end">
                            <RoleController allow={["speaker"]}>
                                <a href={useLink(pages.speakerMeetings)} className="btn waves-effect waves-light modal-trigger">
                                    <Message alias="my_meetings" />
                                    <i className="material-icons right">visibility</i>
                                </a>
                            </RoleController>
                            <RoleController allow={["moderator"]}>
                                <ProposedTopicsButton />
                            </RoleController>
                        </div>
                    </div>
                    <hr />
                </div>
                <UserDataForm user={user} />
            </div>
        </div>
    );
}

export default ProfilePage;