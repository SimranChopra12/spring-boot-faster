package canuran.faster.security;

import canuran.common.utils.TreeBuilder;
import canuran.faster.common.vo.DictionaryNode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import canuran.common.utils.TreeUtils;
import canuran.faster.security.vo.AuthorityNode;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Security 用户。
 * 该对象需要缓存和序列化，尽量保持结构稳定。
 *
 * @author canuran
 */
public class SecurityUser implements UserDetails {

    private Long userId;

    private String username;

    private String nickname;

    private String password;

    /**
     * 功能点权限。
     */
    private List<AuthorityNode> authorities;
    private List<AuthorityNode> authorityTree;

    /**
     * 注解中hasRole表达式会调用该方法。
     */
    @Override
    @JsonIgnore
    public List<AuthorityNode> getAuthorities() {
        return authorities;
    }

    public List<AuthorityNode> getAuthorityTree() {
        return authorityTree;
    }

    /**
     * Authority相当于角色。
     */
    public void setAuthorities(List<AuthorityNode> authorities) {
        this.authorities = authorities;

        TreeBuilder<AuthorityNode, List<AuthorityNode>> builder = new TreeBuilder<AuthorityNode, List<AuthorityNode>>()
                .withNodes(authorities)
                .withTreeCreator(ArrayList::new)
                .withKeyGetter(AuthorityNode::getAuthorityId)
                .withParentKeyGetter(AuthorityNode::getParentId)
                .withChildrenGetter(AuthorityNode::getChildren)
                .withChildrenSetter(AuthorityNode::setChildren);

        List<AuthorityNode> tree = builder.build();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
