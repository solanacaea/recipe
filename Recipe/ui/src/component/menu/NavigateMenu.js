import {
    Menu, Icon,
} from 'antd';
import React, { Component } from 'react';
import { Link } from 'react-router-dom';

import './NavigateMenu.css'

const SubMenu = Menu.SubMenu;

export default class NavigateMenu extends Component {

    state = {
        collapsed: false,
        mode: 'inline',
    };

    onCollapse = (collapsed) => {
        this.setState({ collapsed });
    }

    toggle = () => {
        this.setState({
            collapsed: !this.state.collapsed,
        });
    }

    handleClick = e => {
        console.log("click ", e);
    };

    render() {
        return (
            <div>
                <div className={this.props.collapsed ? 'logo logo-hidden' : 'logo'}>
                    <Icon type="taobao-circle" className="user-icon" />
                    <span className={this.props.collapsed ? 'hidden' : 'show'}>心筑月</span>
                </div>
                <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline"
                    onClick={this.handleClick}>
                    <Menu.Item key="/recipe">
                        <Link to="/recipe">
                            <Icon type="pie-chart" />
                            <span>食谱</span>
                        </Link>
                    </Menu.Item>
                    <Menu.Item key="/info">
                        <Link to="/recipe/info">
                            <Icon type="desktop" />
                            <span>信息</span>
                        </Link>
                    </Menu.Item>
                    <SubMenu
                        key="user"
                        title={<span><Icon type="user" /><span>用户</span></span>}
                    >
                        <Menu.Item key="/yna">月乃安</Menu.Item>
                        <Menu.Item key="/yxh">月小黑</Menu.Item>
                        <Menu.Item key="/ljg">鹿胶膏</Menu.Item>
                    </SubMenu>
                    <SubMenu
                        key="statistics"
                        title={<span><Icon type="team" /><span>统计</span></span>}
                    >
                        <Menu.Item key="/new">新用户</Menu.Item>
                        <Menu.Item key="/regular">老用户回购</Menu.Item>
                        <Menu.Item key="/return">退货</Menu.Item>
                        <Menu.Item key="brush">刷单</Menu.Item>
                    </SubMenu>
                    <Menu.Item key="blog">
                        <Icon type="file" />
                        <span>博文</span>
                    </Menu.Item>
                </Menu>
            </div>
        );
    }
}
