import {
    Drawer, Form, Button, Col, Row, Input, 
  } from 'antd';
import React, { Component } from 'react'; 
import DropdownCategory from './DropdownCategory'
import DropdownOptimalStage from './DropdownOptimalStage'
import DropdownOptimalTime from './DropdownOptimalTime'
import DropdownProperty from './DropdownProperty'
import DropdownEfficacy from './DropdownEfficacy'
  
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

      if (this.category != null) {
        const data = param == null ? null : param.category
        this.category.onChange([data]);
      }
      if (this.optimalStage != null) {
        const data = param == null ? null : param.optimalStage
        this.optimalStage.onChange([data]);
      }
      if (this.optimalTime != null) {
        const data = param == null ? null : param.optimalTime
        this.optimalTime.onChange([data]);
      }
      if (this.property != null) {
        const data = param == null ? null : param.property
        this.property.onChange([data]);
      }
      if (this.efficacy != null) {
        const data = param == null ? null : param.efficacy
        this.efficacy.onChange([data]);
      }
      this.setState({ name: param == null ? null : param.name, });
      this.setState({ content: param == null ? null : param.content, });

    };
  
    onClose = () => {
      this.setState({
        visible: false,
      });
    };

    onRefCategory = (ref) => {
      this.category = ref;
      const data = this.state.data == null ? null : this.state.data.category
      this.category.onChange([data]);
    }
    onRefOptimalStage = (ref) => {
      this.optimalStage = ref;
      const data = this.state.data == null ? null : this.state.data.optimalStage
      this.optimalStage.onChange([data]);
    }
    onRefOptimalTime = (ref) => {
      this.optimalTime = ref;
      const data = this.state.data == null ? null : this.state.data.optimalTime
      this.optimalTime.onChange([data]);
    }
    onRefProperty = (ref) => {
      this.property = ref;
      const data = this.state.data == null ? null : this.state.data.property
      this.property.onChange([data]);
    }
    onRefEfficacy = (ref) => {
      this.efficacy = ref;
      const data = this.state.data == null ? null : this.state.data.efficacy
      this.efficacy.onChange([data]);
    }

    onSubmit = (e) => {
      e.preventDefault();
      //console.log(this.props.form.getFieldsValue("name"));
      //console.log(this.props.form.getFieldsValue("content"));
      console.log(this.category.getValue());
      console.log(this.optimalStage.getValue());
      console.log(this.optimalTime.getValue());
      console.log(this.property.getValue());
      console.log(this.efficacy.getValue());
      console.log(this.state.name);
      console.log(this.state.content);
      console.log(this.state.data);
    };

    callParent = () => {
      return this.state.data;
    }

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
                    <DropdownCategory ref="category" onRef={this.onRefCategory} 
                      callParent={this.callParent} />
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="适宜阶段">
                    <DropdownOptimalStage onRef={this.onRefOptimalStage} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="适宜时间">
                    <DropdownOptimalTime onRef={this.onRefOptimalTime} />
                  </Form.Item>
                </Col>
              </Row>
              <Row gutter={16}>
                <Col span={12}>
                  <Form.Item label="属性">
                    <DropdownProperty onRef={this.onRefProperty} />
                  </Form.Item>
                </Col>
                <Col span={12}>
                  <Form.Item label="功效">
                    <DropdownEfficacy onRef={this.onRefEfficacy} />
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