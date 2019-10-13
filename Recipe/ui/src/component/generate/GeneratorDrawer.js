import {
  Drawer, Form, Button, Col, Row, Input, 
  } from 'antd';
import React, { Component } from 'react'; 
import DropdownEfficacy from '../recipe/DropdownEfficacy'
import axios from 'axios';
import Constants from './Constants.js';
import DropdownIngredient from '../recipe/DropdownIngredient'

class GeneratorDrawer extends Component {
    componentDidMount() {
        this.props.onRef(this);
    }

    state = { visible: false, data: null};

    showDrawer = (param) => {
        this.setState({
          visible: true,
          data: param,
        });
        this.props.form.setFieldsValue({ userName: param == null ? null : param.userName });
        this.props.form.setFieldsValue({ type: (param == null || param.type == null) ? Constants.type : param.type });
        this.props.form.setFieldsValue({ memo: param == null ? null : param.memo });
        this.props.form.setFieldsValue({ feature: (param == null || param.feature == null) ? Constants.feature : param.feature });
        this.props.form.setFieldsValue({ suggestion: param == null ? null : param.suggestion });
        this.props.form.setFieldsValue({ note: (param == null || param.note == null) ? Constants.note : param.note });
        this.props.form.setFieldsValue({ declare: (param == null || param.declare == null) ? Constants.declare : param.declare });
        this.props.form.setFieldsValue({ createdDate: param == null ? null : param.createdDate });

        if (this.efficacy != null) {
          const data = param == null ? null : param.efficacy;
          const arr = (data == null || data === "") ? [] : data.split(',');
          this.efficacy.onChange(arr);
        }
        this.setState({ userName: param == null ? null : param.userName, });
        this.setState({ type: (param == null || param.type == null) ? Constants.type : param.type, });
        this.setState({ memo: param == null ? null : param.memo, });
        this.setState({ feature: (param == null || param.feature == null) ? Constants.feature : param.feature, });
        this.setState({ suggestion: param == null ? null : param.suggestion, });
        this.setState({ note: (param == null || param.note == null) ? Constants.note : param.note, });
        this.setState({ declare: (param == null || param.declare == null) ? Constants.declare : param.declare, });
        this.setState({ createdDate: param == null ? null : param.createdDate });

        if (this.ingredient != null) {
          //this.ingredient.init(param == null ? '素菜' : param.ingredient);
          const data = param == null ? null : param.efficacy;
          const arr = (data == null || data === "") ? [] : data.split(',');
          this.efficacy.onChange(arr);
        }
      };
    
      onClose = () => {
        this.setState({
          visible: false,
        });
      };

      onRefIngredient = (ref) => {
        this.ingredient = ref;
        //const data = this.state.data == null ? null : this.state.data.ingredient;
        const data = this.state.data == null ? null : this.state.data.ingredient;
        const arr = (data == null || data === "") ? [] : data.split(',');
        //this.ingredient.init(data);
        this.ingredient.onChange(arr);
      }

      onRefEfficacy = (ref) => {
        this.efficacy = ref;
        const data = this.state.data == null ? null : this.state.data.efficacy;
        const arr = (data == null || data === "") ? [] : data.split(',');
        this.efficacy.onChange(arr);
      }

      onSubmit = (e) => {
        e.preventDefault();
        //console.log(this.props.form.getFieldsValue("name"));
        //console.log(this.props.form.getFieldsValue("content"));
        const efficacy = this.efficacy.getValue();
        const userName = this.state.userName;
        const type = this.state.type;
        const memo = this.state.memo;
        const feature = this.state.feature;
        const suggestion = this.state.suggestion;
        const note = this.state.note;
        const declare = this.state.declare;
        //const createdDate = this.state.createdDate;
        const ingredient = this.ingredient.getValue();
        const data = this.state.data;
        
        axios.post('http://localhost:8080/dish/generate', {
              id: data == null ? null : data.id, 
              userName: userName,
              type: type,
              memo: memo,
              feature: feature,
              suggestion: suggestion,
              note: note,
              declare: declare,
              efficacy: efficacy.toString(),
              // ingredient: ingredient,
              ingredient: ingredient.toString(),
            }, {
              responseType: 'blob' // very very very important!!!
            }).then((res)=>{
              this.props.refresh();
              this.onClose();

              const content = res.data;
              // console.log(content);
              const blob = new Blob([content]);
              const fileName = userName + '.xlsx';
              if ('download' in document.createElement('a')) { // 非IE下载
                const elink = document.createElement('a');
                elink.download = fileName;
                elink.style.display = 'none';
                elink.href = URL.createObjectURL(blob);
                document.body.appendChild(elink);
                elink.click();
                URL.revokeObjectURL(elink.href); // 释放URL 对象
                document.body.removeChild(elink);
              } else { // IE10+下载
                navigator.msSaveBlob(blob, fileName);
              }

              /*
              const reader = new FileReader();
              const fileName = userName + '.xlsx';
              reader.readAsText(res.data, 'utf-8');
              reader.onload = function (evt) {
                console.log(evt)
                const url = window.URL.createObjectURL(new Blob(['\uFEFF' + evt.target.result], {type: 'application/octet-stream;charset=utf-8'}))
                const link = document.createElement('a')
                link.href = url
                link.setAttribute('download', `${fileName}`)
                document.body.appendChild(link)
                link.click()
              }
              */
              /*
              const content = res.data;
              // console.log(content);
              const blob = new Blob(['\uFEFF' + content]);
              const fileName = userName + '.xlsx';
              if ('download' in document.createElement('a')) { // 非IE下载
                const elink = document.createElement('a');
                elink.download = fileName;
                elink.style.display = 'none';
                elink.href = URL.createObjectURL(blob);
                document.body.appendChild(elink);
                elink.click();
                URL.revokeObjectURL(elink.href); // 释放URL 对象
                document.body.removeChild(elink);
              } else { // IE10+下载
                navigator.msSaveBlob(blob, fileName);
              }
              */
          })
          .catch((err)=>{
              console.log(err)
          })
      };
      
      onNameChange = (e) => {
        this.setState({
          userName: e.target.value,
        });
      }
  
      onTypeChange = (e) => {
        this.setState({
          type: e.target.value,
        });
      }

      onMemoChange = (e) => {
        this.setState({
          memo: e.target.value,
        });
      }

      onFeatureChange = (e) => {
        this.setState({
          feature: e.target.value,
        });
      }

      onSuggestionChange = (e) => {
        this.setState({
          suggestion: e.target.value,
        });
      }

      onNoteChange = (e) => {
        this.setState({
          note: e.target.value,
        });
      }

      onDeclareChange = (e) => {
        this.setState({
          declare: e.target.value,
        });
      }

      render() {
        const { getFieldDecorator } = this.props.form;
        return (
          <div>
            <Drawer
              title="看我不爽吗? 要改掉我吗?"
              height = "800" width = "50%"
              onClose={this.onClose}
              visible={this.state.visible}
              placement="left" keyboard = "true"
            >
              <Form layout="vertical" hideRequiredMark ref={ref => this.form = ref}>
                <Row gutter={16}>
                  <Col span={12}>
                    <Form.Item label="客户名称">
                      {getFieldDecorator('userName', {
                        rules: [
                          {
                            required: true,
                            message: '请输入客户名...',
                          },
                        ],
                      })(<Input rows={8} placeholder="请输入客户名称..." onChange={this.onNameChange}/> )}
                    </Form.Item>
                  </Col>
                  <Col span={12}>
                    <Form.Item label="食谱类别">
                      {getFieldDecorator('type', {
                        rules: [
                          {
                            required: true,
                            message: '请输入食谱类别...',
                          },
                        ],
                      })(<Input rows={8} placeholder="请输入食谱类别..." onChange={this.onTypeChange}/> )}
                    </Form.Item>
                  </Col>
                </Row>
                <Row gutter={16}>
                  <Col span={12}>
                    <Form.Item label="体质">
                      <DropdownEfficacy onRef={this.onRefEfficacy} />
                    </Form.Item>
                  </Col>
                  <Col span={12}>
                    <Form.Item label="原料">
                      <DropdownIngredient onRef={this.onRefIngredient} />
                    </Form.Item>
                  </Col>
                </Row>
                <Row gutter={16}>
                  <Col span={12}>
                    <Form.Item label="备注">
                      {getFieldDecorator('memo', {
                        rules: [
                          {
                            required: true,
                            message: '请输入备注...',
                          },
                        ],
                      })(<Input.TextArea rows={4} placeholder="请输入备注..." onChange={this.onMemoChange}/> )}
                    </Form.Item>
                  </Col>
                  <Col span={12}>
                    <Form.Item label="食谱特点">
                      {getFieldDecorator('feature', {
                        rules: [
                          {
                            required: true,
                            message: '请输入食谱特点...',
                          },
                        ],
                      })(<Input.TextArea rows={4} placeholder="请输入食谱特点..." onChange={this.onFeatureChange}/> )}
                    </Form.Item>
                  </Col>
                </Row>
                <Row gutter={16}>
                  <Col span={24}>
                    <Form.Item label="饮食建议">
                      {getFieldDecorator('suggestion', {
                        rules: [
                          {
                            required: true,
                            message: '请输入饮食建议...',
                          },
                        ],
                      })(<Input.TextArea rows={6} placeholder="请输入饮食建议..." onChange={this.onSuggestionChange}/>)}
                    </Form.Item>
                  </Col>
                </Row>
                <Row gutter={16}>
                  <Col span={12}>
                    <Form.Item label="注意">
                      {getFieldDecorator('note', {
                        rules: [
                          {
                            required: true,
                            message: '请输入注意事项...',
                          },
                        ],
                      })(<Input.TextArea rows={4} placeholder="请输入注意事项..." onChange={this.onNoteChange}/>)}
                    </Form.Item>
                  </Col>
                  <Col span={12}>
                    <Form.Item label="声明">
                      {getFieldDecorator('declare', {
                        rules: [
                          {
                            required: true,
                            message: '请输入声明...',
                          },
                        ],
                      })(<Input.TextArea rows={4} placeholder="请输入声明..." onChange={this.oDeclareChange}/>)}
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
                  取消
                </Button>
                <Button onClick={this.onSubmit} type="primary">
                  生成食谱
                </Button>
              </div>
            </Drawer>
          </div>
        );
      }
}
const App = Form.create()(GeneratorDrawer);
export default App;
