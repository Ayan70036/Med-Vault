# 🏥 MedVault

**MedVault** is a secure healthcare record management platform that enables patients to store medical records, manage doctor access, share emergency information, and maintain a complete audit trail of all healthcare-related activities.

The system is built using **Spring Boot**, **Spring Data JPA**, **MySQL**, **Spring Security**, **Thymeleaf**, **HTML/CSS/JavaScript**, and **AES Encryption** for secure medical record storage.

---

# 📌 Features

## 🔐 User Authentication & Authorization

### Patient Registration
- Register as a patient
- Automatic emergency code generation

### Doctor Registration
- Register as a doctor

### Login System
- Session-based authentication
- Role-based dashboard redirection

### Logout
- Secure session invalidation

---

# 👨‍⚕️ Doctor Dashboard

Doctors can:

### Request Patient Access
- Request temporary access to patient records
- Specify number of access days

### My Patients
- View all approved patients
- Automatically hides expired access grants

### View Medical Records
- Access patient records after approval
- Records are decrypted on retrieval

### View Medical Files
- Access uploaded medical documents
- Download files when access is valid

### Upload Medical Records
- Add diagnosis
- Add prescriptions
- Add treatment notes

### Upload Medical Files
- Upload reports
- Upload scans
- Upload prescriptions
- Upload lab results

### Audit Logs
- View all actions performed by the doctor

---

# 🧑‍⚕️ Patient Dashboard

Patients can:

### View Medical Records
- View all records uploaded by doctors

### View Medical Files
- View uploaded files
- Download files

### Manage Access Requests
- Approve doctor requests
- Reject doctor requests
- Revoke existing access

### Emergency Profile
Manage:
- Blood Group
- Allergies
- Medical Conditions
- Emergency Contact

### Audit Logs
- View account activity history

---

# 🔒 Patient-Controlled Access System

MedVault follows a patient-centric access model.

## Workflow

### 1. Doctor Requests Access

Doctor submits:

```text
Patient Email
Requested Days
```

### 2. Patient Receives Request

Patient can:

- Approve
- Reject

### 3. Access Grant Created

System generates a temporary access grant.

### 4. Doctor Gains Access

Doctor can:

- View Records
- View Files
- Upload Records
- Upload Files

### 5. Automatic Expiry

When access expires:

- View Records ❌
- View Files ❌
- Upload Records ❌
- Upload Files ❌
- Download Files ❌

Access is revoked automatically.

---

# 🚑 Emergency Access System

Every patient receives a unique emergency code.

Example:

```text
EMR-A92X7K
```

Using the emergency code, authorized personnel can retrieve:

- Blood Group
- Allergies
- Medical Conditions
- Emergency Contact

Emergency access does **not** expose:

- Medical Records
- Medical Files
- Doctor Notes

---

# 🔐 Medical Record Encryption

All medical records are encrypted using **AES Encryption** before storage.

### Before Storage

```text
Patient diagnosed with viral fever
```

### Stored in Database

```text
gY72jshs8Jks92kshHsh...
```

Records are decrypted only when accessed by authorized users.

---

# 📁 Medical File Management

### Upload Supported Files

- PDF Reports
- Lab Reports
- Prescriptions
- X-Ray Reports
- MRI Reports
- Medical Scans

### UUID-Based File Storage

Original:

```text
report.pdf
```

Stored:

```text
7d4f4a3c-91fd-report.pdf
```

Benefits:

- Prevents overwriting
- Prevents filename collisions
- Improves file security

---

# 📋 Audit Logging

Every important action is recorded.

### Logged Activities

- Access Requests
- Access Approvals
- Access Revocations
- Record Uploads
- File Uploads
- Emergency Profile Updates

### Example

```text
Requested access for patient@gmail.com
Approved access for doctor@gmail.com
Uploaded file report.pdf
Updated emergency profile
```

---

# 🛡 Security Features

### AES Encryption
Protects medical records stored in the database.

### Session Authentication
Uses HTTP sessions for authenticated access.

### Role-Based Authorization
Separate dashboards for:
- Patients
- Doctors

### Access Validation
Every sensitive operation validates:

- Doctor Exists
- Patient Exists
- Access Grant Exists
- Access Not Expired

### Audit Trail
Tracks all critical system activities.

### UUID File Storage
Prevents file overwriting and predictable filenames.

---

# 🗄 Database Entities

## User

Stores:

- Name
- Email
- Password
- Role
- Emergency Code

---

## MedicalRecord

Stores:

- Doctor
- Patient
- Encrypted Content
- Created Timestamp

---

## MedicalFile

Stores:

- Original Name
- Stored Name
- File Path
- Doctor
- Patient

---

## AccessRequest

Stores:

- Doctor
- Patient
- Requested Days
- Status

---

## AccessGrant

Stores:

- Doctor
- Patient
- Expiry Date

---

## EmergencyProfile

Stores:

- Blood Group
- Allergies
- Conditions
- Emergency Contact

---

## AuditLog

Stores:

- User Email
- Action
- Timestamp

---

# 🏗 Technology Stack

### Backend
- Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security

### Database
- MySQL

### Frontend
- Thymeleaf
- HTML5
- CSS3
- JavaScript

### Security
- AES Encryption
- Session Authentication

### Build Tool
- Maven

---

# 🚀 Future Enhancements

- JWT Authentication
- QR Code Emergency Access
- Email Notifications
- Doctor Verification System
- Cloud File Storage
- File Encryption
- Multi-Factor Authentication
- Appointment Scheduling
- E-Prescriptions
- Medical Analytics Dashboard

---

# 📸 Project Workflow

```text
Patient Registers
        │
        ▼
Doctor Requests Access
        │
        ▼
Patient Approves
        │
        ▼
Temporary Access Granted
        │
        ▼
Doctor Uploads Records & Files
        │
        ▼
Patient Views Medical Data
        │
        ▼
Audit Logs Generated
        │
        ▼
Access Expires / Revoked
```

---

# 👨‍💻 Author

**MedVault - Secure Healthcare Record Management System**

Built using Spring Boot, MySQL, Thymeleaf, and AES Encryption.
