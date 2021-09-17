export const format = (string, data) => {
    return string.replace(/{([^{}]*)}/g,
        function (template, key) {
            const value = data[key];
            return typeof value === "string" || typeof value === "number" ? value : template;
        }
    );
}