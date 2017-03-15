# TaLIa
*Trust Layer Inversion*

The Trust Layer has meaning in both the Semantic Web and IoT and both of these components play key roles in this problem. In IoT, we're dealing with heterogeneous technologies at a scale which render the current system of data confidentiality, authentication, access control, and privacy controls functionally obsolete. The semantic web architecture provides for an encrypted layering of data along with taxomomy and ontology underneath a rules layer to manage logic and proof from which trust can be determined.

It also has a deeper meaning. We are going to approach this problem from the perspective of those who have the least reason to trust the system. This system assumes the primary user is suffering from an orphan disease and goes to a small practitioner. We'll assume the system will scale to someone with a sprained ankle in the NIH. 

Let's start at the data layer. We're moving our primary data storage from schema-on-write to schema-on-read. Apache Hbase is a sparse, distributed, persistent multidimensional sorted map, which is indexed by a row key, column key, and a timestamp. At a basic level, this enables a system that can conform to patient since it is fully flexible. In Hbase, a cell is made up of a key-value pair that's accessed by a row key. A cell can have 0..n tags. These tags can be used as visibility labels and access can be granted based on roles and/or a custom, pluggable algorithm. Basically, its possible to encode multiple layers of tag-based access information directly into a single data point. In fact, the code to do that is included here. 

Normally, Apache Knox handles authentication and Apache Ranger handles authorization. However, I mentioned something about inversion ....

   

