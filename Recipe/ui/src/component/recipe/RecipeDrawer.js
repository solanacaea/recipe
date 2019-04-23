  import {
      Drawer, Form, Button, Col, Row, Input,
    } from 'antd';
  import axios from 'axios';
  import React, { Component } from 'react';

  import PropertyDropdown from './dropdown/PropertyDropdown';
  import constant from '../../core/constant'

  const { options } = constant;

  class DrawerForm extends Component {
    componentDidMount() {
      this.props.onRef(this);
    }

    state = { visible: false, data: null};
    showDrawer = (param) => {
      this.setState({
        visible: true,
        data: param,
      });
      this.props.form.setFieldsValue({ name: param == null ? null : param.name });
      this.props.form.setFieldsValue({ content: param == null ? null : param.content });
      this.setState({ name: param == null ? null : param.name, });
      this.setState({ content: param == null ? null : param.content, });
    };

    onClose = () => {
      this.setState({
        visible: false,
      });
    };

    onSubmit = (e) => {
      e.preventDefault();
      const { category, optimalStage, optimalTime, property, efficacy, name, content, data } = this.state;
      axios.post('http://localhost:8080//dish/save', {
            id: data == null ? null : data.id, 
            name: name,
            content: content,
            category: category.toString(),
            optimalStage: optimalStage.toString(),
            optimalTime: optimalTime.toString(),
            property: property.toString(),
            efficacy: efficacy.toString(),
          }
        ).then((res)=>{
            this.props.refresh();
            this.onClose();
        })
        .catch((err)=>{
            console.log(err)
        });
    };

    onNameChange = (e) => {
      this.setState({
        name: e.target.value,
      });
    }

    onContentChange = (e) => {
      this.setState({
        content: e.target.value,
      });
    }

    onCheckedListChanged = (checkedList, propertyName) => {
      const newState = {};
      newState[propertyName] = checkedList;
      this.setState(newState);
    }

    render() {
      const { getFieldDecorator } = this.props.form;
      return (
        <div>
          <Drawer
            title="看我不爽吗? 要改掉我吗?"
            width={720}
            onClose={this.onClose}
            visible={this.state.visible}
          >
            <Form layout="vertical" hideRequiredMark ref={ref => this.form = ref}>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="名称">
                    {getFieldDecorator('name', {
                      rules: [
                        {
                          required: true,
                          message: '请输入食材名称...',
                        },
                      ],
                    })(<Input rows={8} placeholder="请输入食材名称..." onChange={this.onNameChange}/> )}
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="类别">
                      <PropertyDropdown propertyName={'category'} valueChanged={this.onCheckedListChanged} plainOptions={options.categoryOptions}></PropertyDropdown>
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="适宜阶段">
                    <PropertyDropdown propertyName={'optimalStage'} valueChanged={this.onCheckedListChanged} plainOptions={options.optimalStageOptions}></PropertyDropdown>
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="适宜时间">
                    <PropertyDropdown propertyName={'optimalTime'} valueChanged={this.onCheckedListChanged} plainOptions={options.optimalTimeOptions}></PropertyDropdown>
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="属性">
                    <PropertyDropdown propertyName={'property'} valueChanged={this.onCheckedListChanged} plainOptions={options.propertyOptions}></PropertyDropdown>
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="功效">
                    <PropertyDropdown propertyName={'efficacy'} valueChanged={this.onCheckedListChanged} plainOptions={options.efficacyOptions}></PropertyDropdown>
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={24}>
                  <Form.Item label="食材">
                    {getFieldDecorator('content', {
                      rules: [
                        {
                          required: true,
                          message: '请输入食材内容...',
                        },
                      ],
                    })(<Input.TextArea rows={9} placeholder="请输入食材内容..." onChange={this.onContentChange}/>)}
                  </Form.Item>
                </Col>
              </Row>
            </Form>
            <div
              style={{
                position: 'absolute',
                left: 0,
                bottom: 0,
                width: '100%',
                borderTop: '1px solid #e9e9e9',
                padding: '10px 16px',
                background: '#fff',
                textAlign: 'right',
              }}
            >
              <Button onClick={this.onClose} style={{ marginRight: 8 }}>
                Cancel
              </Button>
              <Button onClick={this.onSubmit} type="primary">
                Submit
              </Button>
            </div>
          </Drawer>
        </div>
      );
    }
  }
  
  const App = Form.create()(DrawerForm);

  export default App;