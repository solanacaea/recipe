import React from 'react'
import { Form, Tabs, Icon, Input, Button, Alert, Row, Col } from 'antd'
import { Link } from 'react-router-dom';

import '../User.css'
import { Captcha } from '../Captcha'
import { LOGIN_AS_ACCOUNT, LOGIN_AS_PHONE } from './Login.type'
import * as UserService from '../../../service/UserService'

const { TabPane } = Tabs;

class LoginForm extends React.Component {

    state = {
        type: LOGIN_AS_ACCOUNT,
        submiting: false,
        status: {
            login_as_account: true,
            login_as_phone: true
        }
    }

    render() {
        const { form } = this.props;
        const { getFieldDecorator } = form;
        return (
            <Form>
                <React.Fragment>
                    <Tabs onChange={(key) => this.onLoginTypeChanged(key)}>
                        <TabPane tab="账号密码登录" key={LOGIN_AS_ACCOUNT}>
                        {
                            this.renderMessage("账户或密码错误", LOGIN_AS_ACCOUNT)
                        }
                            <Form.Item>
                                {getFieldDecorator('username', {
                                    rules: [{ required: true, message: '请输入用户名！' }],
                                })(
                                    <Input
                                    prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                                    placeholder="用户名"
                                    />,
                                )}
                            </Form.Item>
                            <Form.Item>
                                {getFieldDecorator('password', {
                                    rules: [{ required: true, message: '请输入密码！' }],
                                })(
                                <Input
                                prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
                                type="password"
                                placeholder="密码"
                                />,
                            )}
                            </Form.Item>
                        </TabPane>
                        <TabPane tab="手机号登录" key={LOGIN_AS_PHONE}>
                        {
                            this.renderMessage("验证码错误", LOGIN_AS_PHONE)
                        }
                            <Form.Item>
                                {getFieldDecorator('phone', {
                                    rules: [
                                        { required: true, message: '请输入手机号！' },
                                        {
                                            pattern: /^1\d{10}$/,
                                            message: '手机号格式错误！',
                                        }
                                    ],
                                })(
                                    <Input
                                    prefix={<Icon type="phone" style={{ color: 'rgba(0,0,0,.25)' }} />}
                                    placeholder="手机号"
                                    />,
                                )}
                            </Form.Item>
                            <Captcha form={this.props.form} getCaptcha={this.getCaptcha}></Captcha>
                        </TabPane>
                    </Tabs>
                </React.Fragment>
                <Form.Item>
                    <Row gutter={8}>
                        <Col span={16}>
                        <Button type="primary" loading={this.state.submiting} htmlType="submit" className="submit" onClick={(e) => this.onLogin(e)}>登录</Button>
                        </Col>
                        <Col span={8} style={{"textAlign": "right"}}>
                            <Link to="/user/register">注册账户</Link>
                        </Col>
                    </Row>
                    
                </Form.Item>
            </Form>
        );
    }

    getCaptcha(phone) {
        return UserService.getCaptcha(phone).then();
    }

    onLogin(e) {
        e.preventDefault();
        const { type } = this.state;
        let activeFields;
        if (type === LOGIN_AS_ACCOUNT) {
            activeFields = ['username', 'password'];
        } else {
            activeFields = ['phone', 'captcha'];
        }

        const { form } = this.props;
        if (!form) {
            return;
        }

        form.validateFields(activeFields, (err, fieldsValue) => {
            if (err) {
                return;
            }

            this.setState({submiting: true});
            UserService.login(this.getUserLogin(activeFields, fieldsValue, type)).then(value => {
                if (value) {
                    window.location.pathname = '../recipe';
                } else {
                    this.updateState(type, false, false);
                }
            }, err => {
                this.updateState(type, false, false);
            });
        });
    }

    getUserLogin(activeFields, fieldsValue, loginType) {
        const userLogin = {};
        activeFields.forEach(field => {
            userLogin[field] = fieldsValue[field];
        });
        userLogin['type'] = loginType;
        return userLogin;
    }

    updateState(loginType, loginStatus, submiting) {
        const currentState = this.state;
        const status = currentState.status;
        status[loginType] = loginStatus;
        this.setState({status: status, submiting: submiting});
    }

    onLoginTypeChanged(key) {
        this.setState({ type: key});
    }

    renderMessage(content, loginType) {
        if (loginType === this.state.type && !this.state.status[loginType]) {
            return <Alert style={{ marginBottom: 24 }} message={content} type="error" showIcon />
        }
    }
}

export default Form.create({})(LoginForm);