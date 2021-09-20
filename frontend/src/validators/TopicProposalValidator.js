import { checkMinLength } from "./Validator";

export const validate = topicProposal => {
    const errors = [];

    checkMinLength(errors, topicProposal.topicTitle, {
        field: "title",
        min: 5
    });

    return errors;
}