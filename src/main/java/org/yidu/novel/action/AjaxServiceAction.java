package org.yidu.novel.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.BeanUtils;
import org.yidu.novel.action.base.AbstractPublicBaseAction;
import org.yidu.novel.bean.ArticleSearchBean;
import org.yidu.novel.bean.BookcaseSearchBean;
import org.yidu.novel.bean.ChapterSearchBean;
import org.yidu.novel.bean.UserSearchBean;
import org.yidu.novel.cache.CacheManager;
import org.yidu.novel.constant.YiDuConfig;
import org.yidu.novel.constant.YiDuConstants;
import org.yidu.novel.dto.JsonInfoDTO;
import org.yidu.novel.entity.TArticle;
import org.yidu.novel.entity.TBookcase;
import org.yidu.novel.entity.TChapter;
import org.yidu.novel.entity.TUser;
import org.yidu.novel.utils.CookieUtils;
import org.yidu.novel.utils.LoginManager;
import org.yidu.novel.utils.Pagination;
import org.yidu.novel.utils.Utils;

/**
 * 
 * <p>
 * 手机画面用各种ajax接口
 * </p>
 * Copyright(c) 2013 YiDu-Novel. All rights reserved.
 * 
 * @version 1.0.0
 * @author shinpa.you
 */
@Action(value = "ajaxService")
public class AjaxServiceAction extends AbstractPublicBaseAction {

    private static final long serialVersionUID = -5966399886620363535L;

    class ActionType {
        public static final String TOP_LIST = "toplist";
        public static final String CATEGORY_LIST = "categorylist";
        public static final String CHAPTER_LIST = "chapterlist";
        public static final String LOGIN = "login";
        public static final String CHANGE_PASSWORD = "changepassword";
        public static final String BOOKCASEIS_EXISTS = "bookcaseisexists";
        public static final String BOOKCASE = "bookcase";
        public static final String DELETE_BOOKCASE = "deletebookcase";
        public static final String ADD_BOOKCASE = "addbookcase";
        public static final String DELETE_BOOKCASE_BY_ARTICLE = "deletebookcasebyarticle";
        public static final String HISTORY = "history";
        public static final String SEARCH = "search";
        public static final String REGISTER = "register";

    }

    private String action;
    private String loginid;
    private String password;
    private int category;
    private int articleno;
    private int index;
    private int sort;
    private int size;
    private int type;
    private int userno;
    private String bookcasenos;

    private String key;
    private int length;
    private int status;

    private JsonInfoDTO dto = new JsonInfoDTO();

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getArticleno() {
        return articleno;
    }

    public void setArticleno(int articleno) {
        this.articleno = articleno;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserno() {
        return userno;
    }

    public void setUserno(int userno) {
        this.userno = userno;
    }

    public String getBookcasenos() {
        return bookcasenos;
    }

    public void setBookcasenos(String bookcasenos) {
        this.bookcasenos = bookcasenos;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JsonInfoDTO getData() {
        return dto;
    }

    @Override
    public String execute() {
        logger.debug("execute started. ");
        if (StringUtils.equals(action, ActionType.CATEGORY_LIST)) {
            getCategoryList();
        } else if (StringUtils.equals(action, ActionType.CHANGE_PASSWORD)) {
            dochangepass();
        } else if (StringUtils.equals(action, ActionType.CHAPTER_LIST)) {
            getChapterList();
        } else if (StringUtils.equals(action, ActionType.LOGIN)) {
            dologin();
        } else if (StringUtils.equals(action, ActionType.TOP_LIST)) {
            getTopList();
        } else if (StringUtils.equals(action, ActionType.BOOKCASE)) {
            getBookcase();
        } else if (StringUtils.equals(action, ActionType.DELETE_BOOKCASE)) {
            deleteBookcase();
        } else if (StringUtils.equals(action, ActionType.HISTORY)) {
            getHistory();
        } else if (StringUtils.equals(action, ActionType.BOOKCASEIS_EXISTS)) {
            checkBookCaseExists();
        } else if (StringUtils.equals(action, ActionType.ADD_BOOKCASE)) {
            addBookcase();
        } else if (StringUtils.equals(action, ActionType.DELETE_BOOKCASE_BY_ARTICLE)) {
            deleteBookcaseByArticle();
        } else if (StringUtils.equals(action, ActionType.SEARCH)) {
            doSearch();
        } else if (StringUtils.equals(action, ActionType.REGISTER)) {
            register();
        } else {
            dto.setCode(1);
            dto.setErr(getText("errors.unknown"));
        }

        logger.debug("execute normally end. ");
        return JSON_RESULT;
    }

    private void register() {

        logger.info("regedit started.");

        // ID检查
        if (StringUtils.trim(loginid).length() < 5 || StringUtils.trim(loginid).length() > 32) {
            dto.setCode(1);
            dto.setErr(this.getText("errors.lengthrange",
                    new String[] { "5", "32", this.getText("label.user.loginid") }));
            return;
        }
        // 密码检查
        if (StringUtils.trim(password).length() < 5 || StringUtils.trim(password).length() > 32) {
            dto.setCode(1);
            dto.setErr(this.getText("errors.lengthrange",
                    new String[] { "5", "32", this.getText("label.user.password") }));
            return;
        }

        // 重复检查
        UserSearchBean searchBean = new UserSearchBean();
        searchBean.setLoginid(loginid);
        searchBean.setDeleteflag(false);
        List<TUser> userList = this.userService.find(searchBean);
        if (userList != null && userList.size() > 0) {
            dto.setCode(1);
            dto.setErr(this.getText("errors.duplicated", new String[] { this.getText("label.user.loginid") }));

            return;
        }

        TUser user = new TUser();
        user.setLoginid(loginid);
        user.setDeleteflag(false);
        user.setRegdate(new Date());
        user.setLastlogin(new Date());
        user.setPassword(Utils.convert2MD5(password));
        user.setType(YiDuConstants.UserType.NORMAL_USER);
        // 注册用户登录
        this.userService.save(user);
        // 登录处理
        LoginManager.doLogin(user);
        dto.setCode(0);
        dto.setResult("注册成功！");
        logger.debug("regedit normally end.");
    }

    private void doSearch() {
        logger.debug("doSearch start.");

        // 取章节信息
        ArticleSearchBean searchBean = new ArticleSearchBean();
        BeanUtils.copyProperties(this, searchBean);
        if (status == 1) {
            searchBean.setFullflag(true);
        }
        int count = articleService.getCount(searchBean);
        dto.setTotal(count);
        int pages = count / size;
        if (count % size > 0) {
            pages++;
        }
        dto.setPages(pages);
        dto.setSize(size);
        dto.setIndex(index);

        // 添加page信息
        Pagination pagination = new Pagination(size, index + 1);
        switch (sort) {
        case 1:
            pagination.setSortColumn("lastupdate");
            break;
        case 2:
            pagination.setSortColumn("allvisit");
            break;
        case 3:
            pagination.setSortColumn("allvote");
            break;
        case 4:
            pagination.setSortColumn("size");
            break;
        default:
            pagination.setSortColumn("allvisit");
            break;
        }

        pagination.setSortOrder("DESC");
        searchBean.setPagination(pagination);

        List<TArticle> articleList = CacheManager.getObject(CACHE_KEY_ARTICEL_LIST_PREFIX + searchBean.toString());
        if (articleList == null || articleList.size() == 0) {
            articleList = articleService.find(searchBean);
            CacheManager.putObject(CACHE_KEY_ARTICEL_LIST_PREFIX + searchBean.toString(), articleList);
        }
        dto.setItems(articleList);

        logger.debug("doSearch normally end.");

    }

    private void deleteBookcaseByArticle() {
        logger.debug("deleteBookcaseByArticle start.");

        if (articleno == 0) {
            dto.setCode(1);
            return;
        }

        bookcaseService.getByArticleno(LoginManager.getLoginUser().getUserno(), articleno);

        dto.setCode(0);
        logger.debug("deleteBookcaseByArticle normally end.");

    }

    private void addBookcase() {
        logger.debug("addBookcase start.");

        if (articleno == 0) {
            dto.setCode(1);
            return;
        }

        // 检查当前登录的最大件数
        BookcaseSearchBean searchBean = new BookcaseSearchBean();
        searchBean.setUserno(LoginManager.getLoginUser().getUserno());
        int bookcaseCount = this.bookcaseService.getCount(searchBean);
        if (bookcaseCount > YiDuConstants.yiduConf.getInt(YiDuConfig.MAX_BOOKCASE)) {
            dto.setCode(1);
            return;
        }

        TBookcase bookcase = this.bookcaseService.getByArticleno(LoginManager.getLoginUser().getUserno(), articleno);
        if (bookcase == null) {
            bookcase = new TBookcase();
        }

        TArticle article = this.articleService.getByNo(articleno);
        if (article != null && article.getArticleno() != 0) {
            BeanUtils.copyProperties(article, bookcase);
        } else {
            dto.setCode(1);
            return;
        }
        bookcase.setCreatetime(new Date());
        bookcase.setUserno(LoginManager.getLoginUser().getUserno());

        this.bookcaseService.save(bookcase);

        dto.setCode(0);
        logger.debug("addBookcase normally end.");

    }

    private void checkBookCaseExists() {
        logger.debug("checkBookCaseExists start.");

        if (articleno == 0) {
            dto.setCode(1);
            return;
        }

        TBookcase bookcase = bookcaseService.getByArticleno(LoginManager.getLoginUser().getUserno(), articleno);
        if (bookcase != null) {
            dto.setResult("1");
        }
        dto.setCode(0);
        logger.debug("checkBookCaseExists normally end.");

    }

    private void getHistory() {
        logger.debug("getHistory start.");
        // 获得阅读履历
        String historys = CookieUtils.getHistoryCookie(ServletActionContext.getRequest());
        if (StringUtils.isNotEmpty(historys)) {
            String[] acnos = StringUtils.split(historys, ",");
            List<String> articlenoList = new ArrayList<String>();
            for (String articleAndchapterno : acnos) {
                String[] acnoArr = StringUtils.split(articleAndchapterno, "|");
                if (acnoArr.length > 0) {
                    articlenoList.add(acnoArr[0]);
                }
            }
            if (articlenoList.size() > 0) {
                ArticleSearchBean searchBean = new ArticleSearchBean();
                searchBean.setArticlenos(StringUtils.join(articlenoList, ","));
                dto.setItems(articleService.find(searchBean));
            }
            dto.setCode(0);
        }
        logger.debug("getHistory normally end.");
    }

    private void deleteBookcase() {
        logger.debug("deleteBookcase start.");
        if (StringUtils.isEmpty(StringUtils.trim(bookcasenos))) {
            dto.setCode(1);
            return;
        }

        bookcaseService.batchdeleteByNo(bookcasenos, LoginManager.getLoginUser().getUserno());
        dto.setCode(0);
        logger.debug("deleteBookcase normally end.");
    }

    private void getBookcase() {
        logger.debug("getBookcase start.");
        BookcaseSearchBean searchBean = new BookcaseSearchBean();
        searchBean.setUserno(LoginManager.getLoginUser().getUserno());
        // 添加page信息
        Pagination pagination = new Pagination(size, index + 1);
        switch (sort) {
        case 1:
            pagination.setSortColumn("tb.createtime");
            pagination.setSortOrder("DESC");
            break;
        case 2:
            pagination.setSortColumn("ta.lastupdate");
            pagination.setSortOrder("DESC");
            break;
        case 3:
            pagination.setSortColumn("ta.articlename");
            pagination.setSortOrder("ASC");
            break;
        default:
            pagination.setSortColumn("lastupdate");
            pagination.setSortOrder("DESC");
            break;
        }
        searchBean.setPagination(pagination);

        dto.setItems(this.bookcaseService.findWithArticleInfo(searchBean));
        dto.setCode(0);
        logger.debug("getBookcase normally end.");
    }

    private void getTopList() {
        logger.debug("getTopList start.");

        // 取章节信息
        ArticleSearchBean searchBean = new ArticleSearchBean();
        BeanUtils.copyProperties(this, searchBean);
        int count = articleService.getCount(searchBean);
        dto.setTotal(count);
        int pages = count / size;
        if (count % size > 0) {
            pages++;
        }
        dto.setPages(pages);
        dto.setSize(size);
        dto.setIndex(index);

        // 添加page信息
        Pagination pagination = new Pagination(size, index + 1);
        switch (type) {
        case 1:
            pagination.setSortColumn("lastupdate");
            break;
        case 2:
            pagination.setSortColumn("allvisit");
            break;
        case 3:
            pagination.setSortColumn("allvote");
            break;
        case 4:
            pagination.setSortColumn("size");
            break;
        default:
            pagination.setSortColumn("lastupdate");
            break;
        }

        pagination.setSortOrder("DESC");
        searchBean.setPagination(pagination);

        List<TArticle> articleList = CacheManager.getObject(CACHE_KEY_ARTICEL_LIST_PREFIX + searchBean.toString());
        if (articleList == null || articleList.size() == 0) {
            articleList = articleService.find(searchBean);
            CacheManager.putObject(CACHE_KEY_ARTICEL_LIST_PREFIX + searchBean.toString(), articleList);
        }
        dto.setItems(articleList);

        logger.debug("getTopList normally end.");

    }

    private void getCategoryList() {
        logger.debug("getCategoryList start.");

        // 取章节信息
        ArticleSearchBean searchBean = new ArticleSearchBean();
        BeanUtils.copyProperties(this, searchBean);
        int count = articleService.getCount(searchBean);
        dto.setTotal(count);
        int pages = count / size;
        if (count % size > 0) {
            pages++;
        }
        dto.setPages(pages);
        dto.setSize(size);
        dto.setIndex(index);

        // 添加page信息
        Pagination pagination = new Pagination(size, index + 1);
        pagination.setSortColumn("lastupdate");
        if (sort == 0) {
            pagination.setSortOrder("DESC");
        } else {
            pagination.setSortOrder("ASC");
        }
        searchBean.setPagination(pagination);

        List<TArticle> articleList = CacheManager.getObject(CACHE_KEY_ARTICEL_LIST_PREFIX + searchBean.toString());
        if (articleList == null || articleList.size() == 0) {
            articleList = articleService.find(searchBean);
            CacheManager.putObject(CACHE_KEY_ARTICEL_LIST_PREFIX + searchBean.toString(), articleList);
        }
        dto.setItems(articleList);

        logger.debug("getCategoryList normally end.");
    }

    private void getChapterList() {
        logger.debug("getChapterList start.");

        // 取章节信息
        ChapterSearchBean searchBean = new ChapterSearchBean();
        BeanUtils.copyProperties(this, searchBean);
        int count = chapterService.getCount(searchBean);
        dto.setTotal(count);
        int pages = count / size;
        if (count % size > 0) {
            pages++;
        }
        dto.setPages(pages);

        // 添加page信息
        Pagination pagination = new Pagination(size, index + 1);
        pagination.setSortColumn("chapterno");
        if (sort == 0) {
            pagination.setSortOrder("DESC");
        } else {
            pagination.setSortOrder("ASC");
        }
        searchBean.setPagination(pagination);

        List<TChapter> chapterList = CacheManager.getObject(CACHE_KEY_CHAPTER_LIST_PREFIX + searchBean.toString());
        if (chapterList == null || chapterList.size() == 0) {
            chapterList = chapterService.find(searchBean);
            if (chapterList != null && chapterList.size() != 0) {
                CacheManager.putObject(CACHE_KEY_CHAPTER_LIST_PREFIX + searchBean.toString(), chapterList);
            }
        }
        dto.setItems(chapterList);
        if (articleno != 0) {
            articleService.updateVisitStatistic(articleno);
        }
        logger.debug("getChapterList normally end.");

    }

    private void dochangepass() {
        // TODO 输入检查

        TUser user = userService.getByNo(userno);
        if (StringUtils.equals(user.getPassword(), Utils.convert2MD5(password))) {
            dto.setCode(1);
            return;
        }

        if (StringUtils.isNotEmpty(password)) {
            user.setPassword(Utils.convert2MD5(password));
        }
        userService.save(user);
        dto.setCode(0);
        // TODO 文字检查和密码校对
    }

    private void dologin() {
        logger.info("dologin start.");
        TUser user = userService.findByLoginInfo(loginid, Utils.convert2MD5(password));
        if (user != null && user.getDeleteflag() != null && !user.getDeleteflag()) {
            // 正常登录
            LoginManager.doLogin(user);
            // 更新用户最后登录时间
            user.setLastlogin(new Date());
            userService.save(user);
            Cookie cookie = CookieUtils.addUserCookie(user);
            // 添加cookie到response中
            ServletActionContext.getResponse().addCookie(cookie);
            dto.setCode(0);
            logger.debug("dologin normally end.");
        } else {
            dto.setCode(1);
            dto.setErr(getText("errors.login.failed"));
            logger.debug("dologin abnormally end.");
        }

    }

    @Override
    public int getPageType() {
        return YiDuConstants.Pagetype.PAGE_OTHERS;
    }

    @Override
    protected void loadData() {
    }
}