import { useState } from "react";

export const useForceUpdate = () => {
    const [value, setValue] = useState(0);

    return () => {
        if (value > 9) {
            setValue(0);
        } else {
            setValue(value + 1);
        }
    };
};