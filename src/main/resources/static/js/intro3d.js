function $(s){return document.querySelector(s)}
/* ---------- 3D ---------- */
var DE=7.2,DN=0.05,UM=1.65,dA=DE,dT=DE,enInt=false,sc,cam,ren,grp,mL,mN,drag=false,pv={x:0,y:0},vx=0,vy=0,pinch=null,ok3=false;
function introOn(){return $('#intro').classList.contains('on')}
var mP,spr,pPts,clk;
function init3(){
 try{
  sc=new THREE.Scene();cam=new THREE.PerspectiveCamera(42,innerWidth/innerHeight,0.01,100);cam.position.z=dA;
  ren=new THREE.WebGLRenderer({antialias:true});ren.setClearColor(0x000000,1);ren.setSize(innerWidth,innerHeight);ren.setPixelRatio(Math.min(devicePixelRatio,2));
  $('#c3d').appendChild(ren.domElement);grp=new THREE.Group();sc.add(grp);clk=new THREE.Clock();
  function cvs(n,r0,st){var c=document.createElement('canvas');c.width=c.height=n;var x=c.getContext('2d'),g=x.createRadialGradient(n/2,n/2,r0,n/2,n/2,n/2);st.forEach(function(a){g.addColorStop(a[0],a[1])});x.fillStyle=g;x.fillRect(0,0,n,n);return new THREE.CanvasTexture(c)}
  var tx=cvs(128,0,[[0,'rgba(255,255,255,1)'],[.18,'rgba(210,255,255,.95)'],[.45,'rgba(0,242,255,.45)'],[1,'rgba(0,242,255,0)']]);
  spr=new THREE.Sprite(new THREE.SpriteMaterial({map:cvs(256,10,[[0,'rgba(0,210,235,.18)'],[.55,'rgba(0,160,190,.08)'],[1,'rgba(0,0,0,0)']]),transparent:true,depthWrite:false,blending:THREE.AdditiveBlending,opacity:.85}));spr.scale.set(6.4,6.4,1);sc.add(spr);
  var R=2.05,gb=new THREE.IcosahedronGeometry(R,2),pa=gb.attributes.position,v=new THREE.Vector3();
  for(var i=0;i<pa.count;i++){v.set(pa.getX(i),pa.getY(i),pa.getZ(i)).normalize().multiplyScalar(R);pa.setXYZ(i,v.x,v.y,v.z)}
  var vs='varying float vP;void main(){vec3 nn=normalize(normalMatrix*normalize(position));vP=smoothstep(-0.95,0.8,nn.z);';
  mL=new THREE.ShaderMaterial({transparent:true,depthWrite:false,blending:THREE.AdditiveBlending,uniforms:{col:{value:new THREE.Color(0x22f7ff)},fi:{value:0}},
   vertexShader:vs+'gl_Position=projectionMatrix*modelViewMatrix*vec4(position,1.0);}',
   fragmentShader:'uniform vec3 col;uniform float fi;varying float vP;void main(){gl_FragColor=vec4(col,mix(mix(0.14,0.85,vP),0.24,fi));}'});
  grp.add(new THREE.LineSegments(new THREE.EdgesGeometry(gb,1),mL));
  var u=[],seen={};for(var i=0;i<pa.count;i++){var k=pa.getX(i).toFixed(3)+'_'+pa.getY(i).toFixed(3)+'_'+pa.getZ(i).toFixed(3);if(!seen[k]){seen[k]=1;u.push(pa.getX(i),pa.getY(i),pa.getZ(i))}}
  var nn=u.length/3,sz=new Float32Array(nn);for(var i=0;i<nn;i++)sz[i]=16+Math.random()*14;
  var gn=new THREE.BufferGeometry();gn.setAttribute('position',new THREE.Float32BufferAttribute(u,3));gn.setAttribute('aT',new THREE.BufferAttribute(sz,1));
  mN=new THREE.ShaderMaterial({transparent:true,depthWrite:false,blending:THREE.AdditiveBlending,uniforms:{tx:{value:tx},fi:{value:0}},
   vertexShader:'attribute float aT;'+vs+'vec4 mv=modelViewMatrix*vec4(position,1.0);gl_PointSize=aT*(4.2/max(0.35,-mv.z));gl_Position=projectionMatrix*mv;}',
   fragmentShader:'uniform sampler2D tx;uniform float fi;varying float vP;void main(){vec4 t=texture2D(tx,gl_PointCoord);gl_FragColor=vec4(t.rgb,t.a*mix(mix(0.25,1.0,vP),0.45,fi));}'});
  grp.add(new THREE.Points(gn,mN));
  var N=170,pp=new Float32Array(N*3),ps=new Float32Array(N),pf=new Float32Array(N);
  for(var i=0;i<N;i++){var th=Math.random()*6.2832,ph=Math.acos(2*Math.random()-1),r=R*(1.04+Math.random()*0.28);pp[i*3]=r*Math.sin(ph)*Math.cos(th);pp[i*3+1]=r*Math.sin(ph)*Math.sin(th);pp[i*3+2]=r*Math.cos(ph);ps[i]=7+Math.random()*15;pf[i]=Math.random()*6.2832}
  var gp=new THREE.BufferGeometry();gp.setAttribute('position',new THREE.BufferAttribute(pp,3));gp.setAttribute('aT',new THREE.BufferAttribute(ps,1));gp.setAttribute('aF',new THREE.BufferAttribute(pf,1));
  mP=new THREE.ShaderMaterial({transparent:true,depthWrite:false,blending:THREE.AdditiveBlending,uniforms:{tx:{value:tx},uT:{value:0},fi:{value:0}},
   vertexShader:'attribute float aT;attribute float aF;uniform float uT;varying float vB;void main(){vB=0.45+0.55*sin(uT*1.8+aF);vec4 mv=modelViewMatrix*vec4(position,1.0);gl_PointSize=aT*(4.0/max(0.45,-mv.z));gl_Position=projectionMatrix*mv;}',
   fragmentShader:'uniform sampler2D tx;uniform float fi;varying float vB;void main(){vec4 t=texture2D(tx,gl_PointCoord);gl_FragColor=vec4(t.rgb,t.a*vB*(1.0-fi*0.75));}'});
  pPts=new THREE.Points(gp,mP);grp.add(pPts);ok3=true;
 }catch(e){ok3=false;$('#desc').classList.add('activa');$('#hint').style.display='none'}
}
function upd(z){var p=Math.min(1,Math.max(0,(DE-z)/(DE-UM)));
 if(ok3){mL.uniforms.fi.value=p;mN.uniforms.fi.value=p;mP.uniforms.fi.value=p;spr.material.opacity=.85*(1-p*.75)}
 $('#hint').classList.toggle('gone',p>.25);$('#top3').classList.toggle('gone',p>.25);
 if(z<=UM&&!enInt){enInt=true;$('#desc').classList.add('activa');$('#desc').setAttribute('aria-hidden','false')}
 else if(z>UM&&enInt){enInt=false;$('#desc').classList.remove('activa');$('#desc').setAttribute('aria-hidden','true')}}
function loop(){requestAnimationFrame(loop);if(!ok3||!introOn())return;
 mP.uniforms.uT.value=clk.getElapsedTime();pPts.rotation.y-=0.0006;dA+=(dT-dA)*0.075;cam.position.z=dA;upd(dA);
 if(!drag){grp.rotation.y+=(enInt?0.0011:0.0024)+vy;grp.rotation.x+=vx;vx*=.92;vy*=.92}else{grp.rotation.y+=vy;grp.rotation.x+=vx;vx=0;vy=0}
 grp.rotation.x=Math.max(-1.15,Math.min(1.15,grp.rotation.x));ren.render(sc,cam)}
addEventListener('wheel',function(e){if(!introOn()||!ok3)return;var d=e.deltaY;
 if(enInt){if(d<0&&$('#desc').scrollTop<=0)dT=Math.min(9.5,dT+Math.abs(d)*0.0135);return}
 dT=Math.max(DN,Math.min(9.5,dT-d*0.0045))},{passive:true});
$('#c3d').addEventListener('mousedown',function(e){drag=true;pv={x:e.clientX,y:e.clientY}});
addEventListener('mousemove',function(e){if(!drag)return;vy=(e.clientX-pv.x)*0.005;vx=(e.clientY-pv.y)*0.005;pv={x:e.clientX,y:e.clientY}});
addEventListener('mouseup',function(){drag=false});
$('#c3d').addEventListener('touchstart',function(e){if(e.touches.length===1){drag=true;pv={x:e.touches[0].clientX,y:e.touches[0].clientY}}else if(e.touches.length===2){drag=false;pinch=Math.hypot(e.touches[0].clientX-e.touches[1].clientX,e.touches[0].clientY-e.touches[1].clientY)}},{passive:true});
$('#c3d').addEventListener('touchmove',function(e){if(e.touches.length===1&&drag){vy=(e.touches[0].clientX-pv.x)*0.006;vx=(e.touches[0].clientY-pv.y)*0.006;pv={x:e.touches[0].clientX,y:e.touches[0].clientY}}
 else if(e.touches.length===2&&pinch!==null){var c=Math.hypot(e.touches[0].clientX-e.touches[1].clientX,e.touches[0].clientY-e.touches[1].clientY);dT=Math.max(DN,Math.min(9.5,dT-(c-pinch)*0.022));pinch=c}},{passive:true});
$('#c3d').addEventListener('touchend',function(){drag=false;pinch=null});
addEventListener('resize',function(){if(!ok3)return;cam.aspect=innerWidth/innerHeight;cam.updateProjectionMatrix();ren.setSize(innerWidth,innerHeight)});
init3();loop();
