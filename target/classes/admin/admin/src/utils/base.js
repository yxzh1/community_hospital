const base = {
    get() {
        return {
            url : "http://localhost:8080/hospital/",
            name: "hospital",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/hospital/front/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "医院医疗工作运营系统"
        } 
    }
}
export default base
