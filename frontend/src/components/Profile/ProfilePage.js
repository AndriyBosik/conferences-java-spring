import React, { useEffect, useRef, useState } from "react";
import { pages } from "../../constants/pages";
import { useLink } from "../../hooks/useLink";
import { useTitle } from "../../hooks/useTitle";
import { useMessage } from "../../hooks/useMessage";
import RoleController from "../RoleController/RoleController";
import { getUser } from "./../../handler/StorageHandler";
import UserDataForm from "./UserDataForm";
import M from "materialize-css";
import ProposedTopicsButton from "./ProposedTopicsButton";
import { saveAvatar } from "./../../services/FileService";
import { getUserAvatar } from "./../../handler/ImageHandler";
import { DEFAULT_AVATAR } from "./../../constants/defaults";

function ProfilePage() {
    useTitle("profile");

    const photoInputRef = useRef();

    const changePhotoMessage = useMessage("change_photo");
    const profileMessage = useMessage("profile");
    const myMeetingsMessage = useMessage("my_meetings");

    const changeAvatarLink = useLink(pages.changeAvatar);
    const speakerMeetingsLink = useLink(pages.speakerMeetings);

    const [user, setUser] = useState(null);
    const [state, setState] = useState(1);

    useEffect(() => {
        M.updateTextFields();
        const fetchUser = async () => {
            setUser(getUser());
        }

        fetchUser();
    }, []);

    const clickFile = () => {
        photoInputRef.current.click();
    }

    const changePhoto = async event => {
        const file = event.target.files[0];

        const result = await saveAvatar(file);

        if (result.status === "success") {
            setUser(user => {
                return {
                    ...user,
                    imagePath: DEFAULT_AVATAR
                }
            });
            setUser(getUser());
            setState(produceNewState);
        }
    }
    
    if (user == null) {
        return <div></div>
    }

    return (
        <div className="container">
            <div className="row">
                <div className="col s12 my20">
                    <div className="s-vflex m-hflex">
                        <div className="equal-flex s-hflex-center m-hflex-end px10">
                            <div className="z-depth-1 user-profile-avatar stretch-background border50p" style={{
                                backgroundImage: `url('${getUserAvatar(user.imagePath)}')`
                            }}></div>
                        </div>
                        <div className="equal-flex s-hflex-center m-hflex-start px10">
                            <div className="s-vflex-center">
                                <form action={changeAvatarLink} method="post" encType="multipart/form-data">
                                    <input type="hidden" value={state} />
                                    <div className="file-field input-field">
                                        <div className="btn waves-effect waves-light">
                                            <span onClick={clickFile}>{changePhotoMessage}</span>
                                            <input id="user-avatar" type="file" name="avatar" accept="image/*" className="hidden" onChange={changePhoto} ref={photoInputRef} />
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
                            <h4 className="grey-text text-darken-2 mb0">{profileMessage}</h4>
                        </div>
                        <div className="s-vflex-end">
                            <RoleController allow={["speaker"]}>
                                <a href={speakerMeetingsLink} className="btn waves-effect waves-light modal-trigger">
                                    {myMeetingsMessage}
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

const produceNewState = state => {
    if (state < 10) {
        return state + 1;
    }
    return 0;
}