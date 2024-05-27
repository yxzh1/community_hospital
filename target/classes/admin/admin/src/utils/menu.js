const menu = {
  list() {
    return [
      {
        backMenu: [
          {
            child: [
              {
                buttons: ["查看", "新增", "删除", "修改"],
                menu: "病人类型管理",
                menuJump: "列表",
                tableName: "dictionaryBingren",
              },
              {
                buttons: ["查看", "新增", "删除", "修改"],
                menu: "医院资讯类型管理",
                menuJump: "列表",
                tableName: "dictionaryGonggao",
              },
              {
                buttons: ["查看", "新增", "删除", "修改"],
                menu: "科室管理",
                menuJump: "列表",
                tableName: "dictionaryKeshi",
              },
              {
                buttons: ["查看", "新增", "删除", "修改"],
                menu: "药品类型管理",
                menuJump: "列表",
                tableName: "dictionaryYaopin",
              },
              {
                buttons: ["查看", "新增", "删除", "修改"],
                menu: "职位管理",
                menuJump: "列表",
                tableName: "dictionaryZhiwei",
              },
            ],
            menu: "类型管理",
          },
          {
            child: [
              {
                buttons: ["查看", "新增", "修改", "删除"],
                menu: "医院资讯管理",
                menuJump: "列表",
                tableName: "gonggao",
              },
            ],
            menu: "医院资讯管理",
          },
          {
            child: [
              {
                buttons: ["查看", "新增", "修改", "删除"],
                menu: "药品管理",
                menuJump: "列表",
                tableName: "yaopin",
              },
            ],
            menu: "药品管理",
          },
          {
            child: [
              {
                buttons: ["查看", "新增", "修改", "删除"],
                menu: "医生管理",
                menuJump: "列表",
                tableName: "yisheng",
              },
            ],
            menu: "医生管理",
          },
          {
            child: [
              {
                buttons: ["查看", "新增", "修改", "删除"],
                menu: "病人管理",
                menuJump: "列表",
                tableName: "bingren",
              },
              {
                buttons: ["查看", "新增", "修改", "删除"],
                menu: "病例管理",
                menuJump: "列表",
                tableName: "bingli",
              },
            ],
            menu: "病人管理",
          },
          {
            child: [
              {
                buttons: ["查看", "新增", "修改", "删除"],
                menu: "用户管理",
                menuJump: "列表",
                tableName: "yonghu",
              },
            ],
            menu: "用户管理",
          },
        ],
        frontMenu: [],
        hasBackLogin: "是",
        hasBackRegister: "否",
        hasFrontLogin: "否",
        hasFrontRegister: "否",
        roleName: "管理员",
        tableName: "users",
      },
      {
        backMenu: [
          {
            child: [
              {
                buttons: ["查看"],
                menu: "医院资讯管理",
                menuJump: "列表",
                tableName: "gonggao",
              },
            ],
            menu: "医院资讯管理",
          },
          {
            child: [
              {
                buttons: ["查看"],
                menu: "医生咨询管理",
                menuJump: "列表",
                tableName: "yishengChat",
              },
            ],
            menu: "医生咨询管理",
          },
          {
            child: [
              {
                buttons: ["查看"],
                menu: "病人管理",
                menuJump: "列表",
                tableName: "bingren",
              },
              {
                buttons: ["查看", "新增", "修改"],
                menu: "病例管理",
                menuJump: "列表",
                tableName: "bingli",
              },
            ],
            menu: "病人管理",
          },
          {
            child: [
              {
                buttons: ["查看", "订单"],
                menu: "医生挂号管理",
                menuJump: "列表",
                tableName: "yishengGuahao",
              },
            ],
            menu: "医生挂号管理",
          },
        ],
        frontMenu: [],
        hasBackLogin: "是",
        hasBackRegister: "否",
        hasFrontLogin: "否",
        hasFrontRegister: "否",
        roleName: "医生",
        tableName: "yisheng",
      },
    ];
  },
};
export default menu;
