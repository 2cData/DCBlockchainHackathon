#Orphans in The Desert
**Uncommon is not unimportant**

Accenture commissioned a seven-country survey of 7,840 consumers ages 18+ to assess their attitudes toward health, the healthcare system, electronic health records, healthcare technology and their healthcare providers’ electronic capabilities for their 2016 Consumer Survey on Patient Engagement. They found that 92% of consumers felt they should have full access to their EHR while only 18% of doctors felt the same. Interestingly, in 2012, 31% of doctors felt consumers should have full access to their medical records while only 84% of patients felt the same. 

The consumer's perspective is empirically correct: my records are mine. However, it doesn't make sense that doctors as a group over time have become more autocratic. The doctor's change in perspective is driven by experience with inefficient, impersonal systems that don't communicate with each other. Ask a doctor if Medicare's "meaningful use" requirement, that enforces a minimum number of recorded objectives to demonstrate that they are using EMRs properly, has helped streamline their practice. EMR system are more expensive than charts and typically less flexible, either being very limited in their operational parameters or overly complex.

In the US, an Orphan Disease is a condition that affects less than 200,000 people. For 1/3 of people with an orphan disease, getting an accurate diagnosis can take from 1 to 5 years. It took 25 years for my wife. Patients must often travel long distances to find a doctor knowledgeable about their illness, and that doctor may only be able to manage a certain part of their treatment. Orphan diseases are often chronic conditions with multiple chronic co-morbidities that may affect a patient simultaneously. The lifetime economic burden from an uncontrolled orphan disease has been estimated at just under $900,000. Our personal experience has shown the cost burdens in addition to the poor quality of life lead to depression and worse. Just as needless are those who do not survive due to uncommon, but not unknowable, vulnerabilities.

Nothing is more important to a patient than prompt access to current data. When you are healthy or suffer from a condition that is readily identified in the standard patient ingest form, stale data is a nuisance. When you suffer from an orphan disease, this lack of clear, concise data that can be communicated to a doctor with no direct knowledge of your condition can result in being dismissed, not getting a referral to a specialist, or being improperly treated. 

Using schema-on-write data stores and inflexible mechanisms for data governance and master data management, IT is most directly responsible for the state of EMR and its impact on EHR. If the patient must conform to the system for a successful outcome, any patient who does not conform must expect an unsuccessful outcome. 

We can do better. 

In the _Blockchain For Health Data and Its Potential Use in Health IT and Health Care Related Research_ paper: 

> Our proposal involves the use of a public blockchain as an access-control manager to health records that are stored off  blockchain.    
>There are currently no open standards or implementations of blockchain that utilize this approach but research supports the feasibility of the proposed solution.

 
#TaLIa
*Trust Layer Inversion*

The Trust Layer has meaning in both the Semantic Web and IoT and both of these components play key roles in this problem. In IoT, we're dealing with heterogeneous technologies at a scale which render the current system of data confidentiality, authentication, access control, and privacy controls functionally obsolete. The semantic web architecture provides for an encrypted layering of data along with taxomomy and ontology underneath a rules layer to manage logic and proof from which trust can be determined.

It also has a deeper meaning. We are going to approach this problem from the perspective of those who have the least reason to trust the system. This system assumes the primary user is suffering from an orphan disease and goes to a small practitioner. We'll assume the system will scale to someone with a sprained ankle in the NIH. 

Let's start at the data layer. We're moving our primary data storage from schema-on-write to schema-on-read. Apache Hbase is a sparse, distributed, persistent multidimensional sorted map, which is indexed by a row key, column key, and a timestamp. At a basic level, this enables a system that can conform to patient since it is fully flexible. In Hbase, a cell is made up of a key-value pair that's accessed by a row key. A cell can have 0..n tags. These tags can be used as visibility labels and access can be granted based on roles and/or a custom, pluggable algorithm. Basically, its possible to encode multiple layers of tag-based access information directly into a single data point. In fact, the code to do that is included here. 

Normally, Apache Knox handles authentication and Apache Ranger handles authorization. However, I mentioned something about inversion ....
a
   

