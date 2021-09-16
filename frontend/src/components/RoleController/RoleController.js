import { getUserRole } from "./../../handler/StorageHandler";

function RoleController({children, allow}) {
    return (
        allow.includes(getUserRole()) ? children : null
    );
}

export default RoleController;