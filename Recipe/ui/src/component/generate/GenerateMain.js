import {
    Table, Input, Button, Icon, Tag, Tooltip 
  } from 'antd';
  import Highlighter from 'react-highlight-words';
  import React, { Component } from 'react';
  import GeneratorDrawer from './GeneratorDrawer';
  import axios from 'axios';
  import moment from 'moment'; 

  const ButtonGroup = Button.Group;
  
  class GenerateMain extends Component {
    componentDidMount() {
      this.refresh();
    }

    state = {
      searchText: '',
      filteredInfo: null,
      sortedInfo: null,
      selectedRow: null,
      data: null,
      loading: false,
    };
  
    getColumnSearchProps = (dataIndex) => ({
      filterDropdown: ({
        setSelectedKeys, selectedKeys, confirm, clearFilters,
      }) => (
        <div style={{ padding: 8 }}>
          <Input
            ref={node => { this.searchInput = node; }}
            placeholder={`Search ${dataIndex}`}
            value={selectedKeys[0]}
            onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
            onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
            style={{ width: 188, marginBottom: 8, display: 'block' }}
          />
          <Button
            type="primary"
            onClick={() => this.handleSearch(selectedKeys, confirm)}
            icon="search"
            size="small"
            style={{ width: 90, marginRight: 8 }}
          >
            Search
          </Button>
          <Button
            onClick={() => this.handleReset(clearFilters)}
            size="small"
            style={{ width: 90 }}
          >
            Reset
          </Button>
        </div>
      ),
      filterIcon: filtered => <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />,
      onFilter: (value, record) => record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
      onFilterDropdownVisibleChange: (visible) => {
        if (visible) {
          setTimeout(() => this.searchInput.select());
        }
      },
      render: (text) => (
        <Highlighter
          highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
          searchWords={[this.state.searchText]}
          autoEscape
          textToHighlight={text == null ? "" : text.toString()}
        />
      ),
    })
  
    handleSearch = (selectedKeys, confirm) => {
      confirm();
      this.setState({ searchText: selectedKeys[0] });
    }
  
    handleReset = (clearFilters) => {
      clearFilters();
      this.setState({ searchText: '' });
    }

    handleChange = (pagination, filters, sorter) => {
      //console.log('Various parameters', pagination, filters, sorter);
      this.setState({
        filteredInfo: filters,
        sortedInfo: sorter,
      });
    }

    clearFilters = () => {
      this.setState({ filteredInfo: null });
    }
  
    clearAll = () => {
      this.setState({
        filteredInfo: null,
        sortedInfo: null,
        selectedRow: null,
      });
    }

    render() {
      let { sortedInfo, filteredInfo } = this.state;
      sortedInfo = sortedInfo || {};
      filteredInfo = filteredInfo || {};

      const columns = [{
        title: '客户',
        dataIndex: 'userName',
        key: 'userName',
        ...this.getColumnSearchProps('userName'),
        width: 120,
      // }, {
      //   title: '食谱类别',
      //   dataIndex: 'type',
      //   key: 'type',
      //   ...this.getColumnSearchProps('type'),
      //   width: 120,
      }, {
        title: '备注',
        dataIndex: 'memo',
        key: 'memo',
        ...this.getColumnSearchProps('memo'),
      // }, {
      //   title: '食谱特点',
      //   dataIndex: 'feature',
      //   key: 'feature',
      //   ...this.getColumnSearchProps('feature'),
      //   //textWrap: 'word-break', ellipsis: true,
      //   render: largeText,
      }, {
        title: '饮食建议',
        dataIndex: 'suggestion',
        key: 'suggestion',
        ...this.getColumnSearchProps('suggestion'),
        //textWrap: 'word-break', ellipsis: true,
        render: largeText,
      // }, {
      //   title: '注意',
      //   dataIndex: 'note',
      //   key: 'note',
      //   ...this.getColumnSearchProps('note'),
      //   render: largeText,
      // }, {
      //   title: '声明',
      //   dataIndex: 'declare',
      //   key: 'declare',
      //   ...this.getColumnSearchProps('declare'),
        /*
        render: (text, record) => (
          <div style={{ wordWrap: 'break-word', wordBreak: 'break-word', 
              display: 'WebkitBox', WebkitLineClamp: 2, WebkitBoxOrient: "vertical",
              overflow: 'hidden', textOverflow: 'ellipsis' }}>
            {text}
          </div>
        ),
        */
        // render: largeText,
      }, {
        title: '功效',
        dataIndex: 'efficacy',
        key: 'efficacy',
        filters: [
          { text: '补血', value: '补血' },
          { text: '活血', value: '活血' },
          { text: '清热', value: '清热' },
          { text: '祛湿', value: '祛湿' },
        ],
        filteredValue: filteredInfo.efficacy || null,
        onFilter: (value, record) => record.efficacy.includes(value),
        sorter: (a, b) => a.efficacy.length - b.efficacy.length,
        sortOrder: sortedInfo.columnKey === 'efficacy' && sortedInfo.order,
        render: efficacy => (
            <span>
              {efficacy.split(',').map(tag => {
                var color;
                if (tag === '祛湿')
                    color = 'geekblue';
                else if (tag === '清热')
                    color = 'green';
                else if (tag === '补血')
                    color = 'blue';
                else if (tag === '活血')
                    color = 'volcano';
                
                return (
                  <Tag color={color} key={tag}>
                    {tag.toUpperCase()}
                  </Tag>
                );
              })}
            </span>
          ),
      }, {
        title: '生成日期',
        dataIndex: 'createdDate',
        key: 'createdDate',
        ...this.getColumnSearchProps('createdDate'),
        render: formatterTime,
        width: 120,
      }, {
        title: '操作',
        key: 'action',
        render: (text, record) => (
          <span>
            <ButtonGroup size="small">
                <Button onClick={this.openPanel.bind(this, record)} >
                    <Icon type="edit" theme="filled" />
                </Button>
            </ButtonGroup>
          </span>
         ),
      }];
      return <div> 
              <Button block icon="plus" width="40px" onClick={this.openPanel.bind(this, null)}>生成食谱</Button>
              <Table rowKey={record => record.id} columns={columns} dataSource={this.state.data} 
                bordered onChange={this.handleChange} 
                pagination={{ pageSize: 50 }}/>
              <GeneratorDrawer onRef={this.onRef} refresh={this.refresh}/>
            </div>;
    }
    
    onRef = (ref) => {
      this.drawer = ref
    }

    openPanel = (record) => {
        this.drawer.showDrawer(record);
    }
    
    refresh = () => {
      axios.post('http://localhost:8080/dish/generate/getall')
      .then((res) => {
        //console.log(res);
        this.setState({data: res.data});
        //this.handleToggle('loading')
      })
      .catch((err) => {
        console.log(err)
      })
    }

    handleToggle = prop => enable => {
        this.setState({ [prop]: enable });
    };

  }

    const formatterTime = (val) => {
        return val ? moment(val).format('YYYY-MM-DD HH:mm') : ''
    }

    const largeText = (val) => {
      var tip = val;
      if (val && val.length > 15)
        val = val.slice(0, 15) + '...';
        
      return <Tooltip title={tip}>
          <span>{val}</span>
        </Tooltip>
    }

  export default GenerateMain;
