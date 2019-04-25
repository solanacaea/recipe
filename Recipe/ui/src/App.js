import React, { Component } from 'react';
import './App.css';
import { BrowserRouter } from 'react-router-dom';
import NavigateMenu from "./component/menu/NavigateMenu";//导航
import CountentRouter from './component/router/ContentRouter'//主题
import './App.css';
import 'antd/dist/antd.css';
import logo from './logo.svg';

//UI-antd-按需引入
import 'antd/dist/antd.css';

import {
  Layout, Breadcrumb, Icon,
} from 'antd';
const {
  Header, Content, Footer, Sider,
} = Layout;

//let screenHeight= window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

class test extends Component {

  state = {
    collapsed: false
  }

  onCollapse = (collapsed) => {
    this.setState({ collapsed });
  }

  toggle = () => {
    this.setState({
      collapsed: !this.state.collapsed,
    });
  }

  render() {
    return (
      <BrowserRouter>
        <Layout style={{ minHeight: '100vh' }}>
          <Sider
            collapsible
            collapsed={this.state.collapsed}
            onCollapse={this.onCollapse}
          >
            <NavigateMenu collapsed={this.state.collapsed}/>
          </Sider>
          <Layout>
            <Header style={{ background: '#000', padding: 0 }}>
              <span style={{color:'#fff', paddingLeft:'2%', fontSize:'1.4em'}}>
                  <Icon
                      className="trigger"
                      type={this.state.collapsed ? 'menu-unfold' : 'menu-fold'}
                      onClick={this.toggle}
                      style={{cursor: 'pointer'}}
                  />
              </span>
              <span style={{color:'#fff', paddingLeft:'2%', fontSize:'1.4em'}}>Information Management System</span>
              <span style={{color:'#fff', float:'right', paddingRight:'1%'}}>
                  <img src={logo} className="App-logo" alt="logo" />
              </span>
            </Header>
            <Content style={{ margin: '0 16px' }}>
              <Breadcrumb style={{ margin: '16px 0' }}>
                <Breadcrumb.Item>食谱</Breadcrumb.Item>
                <Breadcrumb.Item>列表</Breadcrumb.Item>
              </Breadcrumb>
              <div style={{ padding: 12, background: '#fff', minHeight: 360 }}>
                <CountentRouter/>
              </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>
              UI Design ©2019 Created by SB Wang
            </Footer>
          </Layout>
        </Layout>
      </BrowserRouter>
    );
  }

}

export default test;
