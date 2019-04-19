import React, { Component } from 'react';
import './App.css';
import { BrowserRouter } from 'react-router-dom';
import CustomMenu from "./component/menu/CustomMenu";//导航
import ContentMain from './component/CustomMain/ContentMain'//主题
import './App.css';
import 'antd/dist/antd.css';
import logo from './logo.svg';
import './Navi.css'

//UI-antd-按需引入
import 'antd/dist/antd.css';

import {
  Layout, Menu, Breadcrumb, Icon,
} from 'antd';
const {
  Header, Content, Footer, Sider,
} = Layout;
const SubMenu = Menu.SubMenu;

//let screenHeight= window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;

class test extends Component {
  constructor(props) {
    super(props)
    this.updateParent= this.updateParent.bind(this);
  }

  state = {
    collapsed: false
  }

  onRef = (ref) => {
    this.myMenu = ref
  }

  updateParent(any) {
    
  }

  toggle = (e) => {
    this.myMenu.toggle()
  }

  onCollapse = (collapsed) => {
    this.setState({ collapsed });
    this.myMenu.setState({
      collapsed: !this.state.collapsed,
    });
  }

  toggle = () => {
    this.setState({
      collapsed: !this.state.collapsed,
    });
    this.myMenu.setState({
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
            <CustomMenu updateParent= {this.updateParent} onRef={this.onRef} />
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
                <ContentMain/>
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
