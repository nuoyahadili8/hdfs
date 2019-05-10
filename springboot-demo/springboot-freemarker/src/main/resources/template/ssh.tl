  <action name="ssh1">
    <ssh xmlns="uri:oozie:ssh-action:0.2">
      <host>billftp@10.210.119.196</host>
      <command>ksh</command>
      <args>${param}</args>
    </ssh>
    <ok to="end"/>
    <error to="fail"/>
  </action>
