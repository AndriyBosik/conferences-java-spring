import React from "react";
import { useMessage } from "../../hooks/useMessage";
import RoleController from "../RoleController/RoleController";
import TopicRow from "./TopicRow";

function TopicsList({topics, user, rowClickHandler, isOutdated, topicModalId}) {
    return (
        <table className="striped highlight z-depth-1">
            <thead>
                <tr>
                    <th className="center-align">#</th>
                    <th>{useMessage("speaker")}</th>
                    <th>{useMessage("topic")}</th>
                    <RoleController allow={["moderator"]}>
                        <th></th>
                    </RoleController>
                </tr>
            </thead>
            <tbody>
                {
                    topics.map((topic, index) => <TopicRow topicModalId={topicModalId} rowClickHandler={rowClickHandler} key={topic.id} topic={topic} order={index + 1} user={user} />)
                }
            </tbody>
        </table>
    );
}

export default TopicsList;