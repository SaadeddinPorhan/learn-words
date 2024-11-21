import React, { useState, useEffect } from 'react';
import { Table, Button, Modal, Form, Input, Select, Popconfirm } from 'antd';
import axios from 'axios';

const { Option } = Select;

const WordsTable = () => {
  const [dataSource, setDataSource] = useState([
  ]);

  const [isModalVisible, setIsModalVisible] = useState(false);
  const [editingRecord, setEditingRecord] = useState(null); // Track the record being edited
  const [form] = Form.useForm();
  const getData = async () => {
    try {
      const response = await axios.get("http://localhost:8080/words/");
      setDataSource(response.data); // Assuming the backend returns a JSON array
    } catch (error) {
      console.error('Error fetching words:', error);
    }
  };
//   const getData = () => {
//     fetch("http://localhost:8080/words/")
//         .then((response) => response.json())
//         .then((data) => {
//             setDataSource(data);
//         });
//     }
    const updateData = async (id, body) => {
        
        await fetch("http://localhost:8080/words/?id="+id, {
            method: "PATCH",
            body: JSON.stringify(body),
            headers: {
              "Content-type": "application/json; charset=UTF-8"
            }
          });
          getData();
    }
    const addData = async (body) => {
        try {
          await axios.post("http://localhost:8080/words/", body);
          // Call getData after successful addition to refresh the list
          getData();
        } catch (error) {
          console.error('Error adding word:', error);
        }
      };
    useEffect(() => {
        getData();
    }, []);
  // Show the modal for adding/editing
  const showModal = (record = null) => {
    setEditingRecord(record);
    if (record) {
      form.setFieldsValue(record); // Populate form for editing
    } else {
      form.resetFields(); // Clear form for adding
    }
    setIsModalVisible(true);
  };

  // Handle form submission
  const handleOk = () => {
    form
      .validateFields()
      .then((values) => {

        if (editingRecord) {
          // Update the existing record
          const updatedEntry = {
            ...values,
          };
          updateData(editingRecord.id,updatedEntry)
          getData();
        } else {
          // Add new record
          const newEntry = {
            ...values,
          };
          addData(newEntry);
        }
        form.resetFields();
        setIsModalVisible(false);
      })
      .catch((info) => {
        console.error('Validation failed:', info);
      });
      
  };

  // Delete record
  const  handleDelete = async (record) => {
    await fetch("http://localhost:8080/words/?id="+record.id, {
        method: "DELETE"
      });
      getData();
  };

  // Get unique languages for filter options
  const getUniqueLanguages = () => {
    const uniqueLanguages = [...new Set(dataSource.map((item) => item.language))];
    return uniqueLanguages.map((lang) => ({
      text: lang,
      value: lang,
    }));
  };

  // Table columns
  const columns = [
    {
      title: 'Word',
      dataIndex: 'word',
      key: 'word',
    },
    {
      title: 'Meaning',
      dataIndex: 'meaning',
      key: 'meaning',
    },
    {
      title: 'Language',
      dataIndex: 'language',
      key: 'language',
      filters: getUniqueLanguages(),
      onFilter: (value, record) => record.language === value, // Filtering logic
    },
    {
      title: 'Created Date',
      dataIndex: 'createdAt',
      key: 'createdAt',
    },
    {
      title: 'Updated Date',
      dataIndex: 'lastUpdated',
      key: 'lastUpdated',
    },
    {
      title: 'Actions',
      key: 'actions',
      render: (_, record) => (
        <span>
          <Button
            type="link"
            onClick={() => showModal(record)}
            style={{ marginRight: 8 }}
          >
            Edit
          </Button>
          <Popconfirm
            title="Are you sure to delete this word?"
            onConfirm={() => handleDelete(record)}
            okText="Yes"
            cancelText="No"
          >
            <Button type="link" danger>
              Delete
            </Button>
          </Popconfirm>
        </span>
      ),
    },
  ];

  // Handle modal cancellation
  const handleCancel = () => {
    form.resetFields();
    setIsModalVisible(false);
  };

  return (
    <div style={{ margin: '20px' }}>
      <h2>Words and Meanings</h2>
      <Button type="primary" onClick={() => showModal()} style={{ marginBottom: '15px' }}>
        Add Word
      </Button>
      <Button type="primary" onClick={() => getData()} style={{ marginBottom: '15px', marginLeft: '15px' }}>
        Refresh Word
      </Button>
      <Table
        dataSource={dataSource}
        columns={columns}
        pagination={{ pageSize: 25 }}
        rowKey="id" // Ensures React handles rows properly
      />

      <Modal
        title={editingRecord ? 'Edit Word' : 'Add New Word'}
        open={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
        okText={editingRecord ? 'Update' : 'Add'}
        cancelText="Cancel"
      >
        <Form form={form} layout="vertical">
          <Form.Item
            name="word"
            label="Word"
            rules={[{ required: true, message: 'Please enter the word!' }]}
          >
            <Input placeholder="Enter the word" />
          </Form.Item>
          <Form.Item
            name="meaning"
            label="Meaning"
            rules={[{ required: true, message: 'Please enter the meaning!' }]}
          >
            <Input placeholder="Enter the meaning" />
          </Form.Item>
          <Form.Item
            name="language"
            label="Language"
            rules={[{ required: true, message: 'Please select the language!' }]}
          >
            <Select placeholder="Select the language">
              <Option value="English">English</Option>
              <Option value="Spanish">Spanish</Option>
              <Option value="French">French</Option>
              <Option value="German">German</Option>
            </Select>
          </Form.Item>
        </Form>
      </Modal>
    </div>
  );
};

export default WordsTable;
