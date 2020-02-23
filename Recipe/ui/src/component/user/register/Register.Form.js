import React from 'react'
import {
    Form,
    Input,
    Button,
    Row,
    Col,
} from 'antd';
import {Link} from 'react-router-dom';

import { Captcha } from '../Captcha'
import * as UserService from '../../../service/UserService';
import '../User.css'

class RegisterForm extends React.Component {
    state = {
        submiting: false
    }

    render() {
        const { form } = this.props;
        const { getFieldDecorator } = form;

        return (
            <Form>
                <Form.Item>
                    <h3>注册</h3>
                </Form.Item>
                <Form.Item>
                    {
                        getFieldDecorator('mail', {
                            rules: [
                                {
                                    type: 'email',
                                    message: '邮箱地址格式错误！',
                                },
                                {
                                    required: true,
                                    message: '请输入邮箱地址！',
                                }
                            ],
                        })(<Input placeholder="邮箱" />,)
                    }
                </Form.Item>
                <Form.Item>
                    {
                        getFieldDecorator('password', {
                            rules: [
                                {
                                    required: true,
                                    message: '请输入密码！',
                                }
                            ]
                        })(<Input type="password" placeholder="至少6位密码，区分大小写" />)
                    }
                </Form.Item>
                <Form.Item>
                    {
                        getFieldDecorator('confirmPassword', {
                            rules: [
                                {
                                    required: true,
                                    message: '请输入密码！',
                                },
                                {
                                    validator: (rule, value, callback) => {
                                        this.checkConfirmPassword(value, callback);
                                    }
                                }
                            ]
                        })(<Input type="password" placeholder="确认密码" />)
                    }
                </Form.Item>
                <Form.Item>
                    {
                        getFieldDecorator('phone', {
                            rules: [
                                { required: true, message: '请输入手机号！' },
                                {
                                    pattern: /^1\d{10}$/,
                                    message: '手机号格式错误！',
                                }
                            ]
                        })(<Input placeholder="手机号" />)
                    }
                </Form.Item>
                <Captcha form={this.props.form} getCaptcha={this.getCaptcha} ></Captcha>
                <Form.Item>
                    <Row gutter={8}>
                        <Col span={12}>
                            <Button type="primary" htmlType="submit" className="submit" loading={this.state.submiting} onClick={(e) => this.onRegister(e)}>注册</Button>
                        </Col>
                        <Col span={8} style={{"float": "right"}}>
                            <Link to="./login">使用已有账户登录</Link>
                        </Col>
                    </Row>
                </Form.Item>
            </Form>
        );
    }

    checkConfirmPassword(value, callback) {
        const { form } = this.props;
        if (!form) {
            return;
        }

        let password = form.getFieldValue('password');
        if (password && password !== value) {
          callback(new Error('两次输入的密码不匹配'));
        } else {
          callback();
        }
    }

    getCaptcha(phone) {
        return UserService.getCaptcha(phone).then();
    }

    onRegister(e) {
        e.preventDefault();
        const { form } = this.props;
        if (!form) {
            return;
        }

        form.validateFields(['mail', 'password', 'confirmPassword', 'phone', 'captcha'],(err, fieldsValue) => {
            if (err) {
                return;
            }

            this.setState({ submiting: true });
            UserService.register(fieldsValue).then(value => {
                if (value) {
                    window.location.pathname = '../recipe';
                } else {
                    this.setState({ submiting: false });
                }
            }, err => {
                this.setState({ submiting: false});
            });
        });
    }
}

export default Form.create({})(RegisterForm);