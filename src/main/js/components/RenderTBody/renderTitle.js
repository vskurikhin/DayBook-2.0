
export const renderTitle = (id, value) => {
    const {type, ...record} = value;
    if (record.title === null)
        return "title for " + id;
    return record.title;
}

export default renderTitle;
