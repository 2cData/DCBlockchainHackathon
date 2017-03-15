# TaLIa
*Trust Layer Inversion*

The Trust Layer has meaning in both the Semantic Web and IoT and both of these components play key roles in this problem. In IoT, we're dealing with heterogeneous technologies at a scale which render the current system of data confidentiality, authentication, access control, and privacy controls functionally obsolete. The semantic web architecture provides for an encrypted layering of data along with taxomomy and ontology underneath a rules layer to manage logic and proof from which trust can be determined.

It also has a deeper meaning. We are going to approach this problem from the perspective of those who have the least reason to trust the system. This system assumes the primary user is suffering from an orphan disease and goes to a small practitioner. We'll assume the system will scale to someone with a sprained ankle in the NIH. 

Let's start at the data layer. We're moving our primary data storage from schema-on-write to schema-on-read. Apache Hbase is a sparse, distributed, persistent multidimensional sorted map, which is indexed by a row key, column key, and a timestamp. At a basic level, this enables a system that can conform to patient since it is fully flexible. In Hbase, a cell is made up of a key-value pair that's accessed by a row key. A cell can have 0..n tags. These tags can be used as visibility labels and access can be granted based on roles and/or a custom, pluggable algorithm. Basically, its possible to encode multiple layers of tag-based access information directly into a single data point. In fact, the code to do that is included here. 

Normally, Apache Knox handles authentication and Apache Ranger handles authorization. However, I mentioned something about inversion ....

As part of MIT's blockchain initiative, Enigma, a paper envisioned blockchain as an automated access control manager to enable a personal data management system. At the core of the paper is the idea of combining blockchain and off-blockchain storage. The goal is to provide clear data ownership, data transparency and auditability and fine-grained access control.

There are three entities: users, services and nodes. The blockchain accepts two types of transactions: access and data. Access transactions are used for data control management. Data transactions are used for data storage and retrieval.

For example, a user installs an application to access EHR from a particular provider. A new compound identity is created (shared user and service), as long with associated permissions to the blockchain as an access transaction. Data collected is encrypted using the shared encryption key and sent to the blockchain in a data transaction, which subsequently routes the data to an off-blockchain key-value store. The blockchain just keeps a SHA-256 hash of the data. Both the service and the user can now query the data with data transactions using just the pointer. The user can change permissions at any time by issuing an access transaction with a new set of permissions.  

From the perspective of a running IT system, placing all of the data governance in a new system like blockhainc, 
  

