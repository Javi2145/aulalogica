/* Mecanografía: captura de teclas y teclado virtual (solo interfaz).
   Al terminar el nivel, Java calcula puntaje, precisión y desbloqueo (POST /mecanografia/{n}/resultado). */
(function () {
  var cont = document.getElementById('tplay');
  if (!cont) return;
  var TEXT = cont.dataset.texto, NIVEL = cont.dataset.nivel, HAYMAS = cont.dataset.haymas === 'true';
  var URL_RES = cont.dataset.url, URL_SIG = cont.dataset.sig, URL_MENU = cont.dataset.menu, URL_ESTE = cont.dataset.este;
  var $ = function (s) { return document.querySelector(s); };
  var ty, K = {}, shiftOn = false;
  var SH = { '!': '1', '@': '2', '#': '3', '$': '4', '%': '5', '^': '6', '&': '7', '*': '8', '(': '9', ')': '0', '_': '-', '+': '=', '{': '[', '}': ']', '|': '\\', ':': ';', '"': "'", '<': ',', '>': '.', '?': '/', '~': '`' }, INV = {};
  for (var s in SH) INV[SH[s]] = s;
  var ROWS = [['`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=', ['Backspace', '⌫', 2]],
    [['Tab', 'Tab', 1.5], 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', ['\\', '\\', 1.5]],
    [['Caps', 'Caps', 1.8], 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', "'", ['Enter', 'Enter', 2.2]],
    [['Shift', 'Shift', 2.4], 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/', ['ShiftR', 'Shift', 2.6]],
    [[' ', 'espacio', 8]]];
  function buildKb() {
    K = {}; var h = '';
    ROWS.forEach(function (r) {
      h += '<div class="kr">';
      r.forEach(function (k) {
        var key = k, lab = k, w = 1;
        if (typeof k !== 'string') { key = k[0]; lab = k[1]; w = k[2]; }
        h += '<button type="button" class="k" data-k="' + key.replace(/"/g, '&quot;') + '" style="flex:' + w + '" tabindex="-1">' + lab + '</button>';
      });
      h += '</div>';
    });
    $('#kb').innerHTML = h;
    document.querySelectorAll('#kb .k').forEach(function (b) {
      K[b.dataset.k] = b;
      b.addEventListener('click', function () { vclick(b.dataset.k); });
    });
  }
  function keyOf(ch) {
    if (ch === ' ') return { k: ' ', s: false };
    if (SH[ch]) return { k: SH[ch], s: true };
    if (ch.length === 1 && ch !== ch.toLowerCase()) return { k: ch.toLowerCase(), s: true };
    return { k: ch, s: false };
  }
  function hintNext() {
    document.querySelectorAll('#kb .k.nx').forEach(function (b) { b.classList.remove('nx'); });
    if (!ty || ty.fin || ty.pos >= TEXT.length) return;
    var o = keyOf(TEXT[ty.pos]);
    if (K[o.k]) K[o.k].classList.add('nx');
    if (o.s) { K.Shift.classList.add('nx'); K.ShiftR.classList.add('nx'); }
  }
  function flash(ch, cls) {
    var o = keyOf(ch), b = K[o.k]; if (!b) return;
    document.querySelectorAll('#kb .k.sel').forEach(function (x) { x.classList.remove('sel'); });
    b.classList.add('sel', cls);
    setTimeout(function () { b.classList.remove(cls); }, 260);
  }
  function vclick(k) {
    if (ty.fin) return;
    if (k === 'Shift' || k === 'ShiftR') {
      shiftOn = !shiftOn; K.Shift.classList.toggle('sel', shiftOn); K.ShiftR.classList.toggle('sel', shiftOn); return;
    }
    if (k.length > 1) return;
    var ch = k;
    if (shiftOn) { ch = INV[k] || k.toUpperCase(); shiftOn = false; K.Shift.classList.remove('sel'); K.ShiftR.classList.remove('sel'); }
    press(ch);
  }
  function esc(c) { return c === ' ' ? '&nbsp;' : c.replace('&', '&amp;').replace('<', '&lt;'); }
  function draw() {
    var h = '';
    for (var i = 0; i < TEXT.length; i++) {
      h += '<span class="' + (i < ty.pos ? 'o' : (i === ty.pos ? 'c' + (ty.bad ? ' x' : '') : '')) + '">' + esc(TEXT[i]) + '</span>';
    }
    $('#ttext').innerHTML = h;
    var tot = ty.ok + ty.err, acc = tot ? Math.round(ty.ok / tot * 100) : 100;
    var pts = Math.max(0, ty.ok * 10 - ty.err * 5);
    var min = ty.t0 ? (Date.now() - ty.t0) / 60000 : 0, ppm = min > 0.01 ? Math.round(ty.ok / 5 / min) : 0;
    $('#tst').innerHTML = '<span>Puntos: <b>' + pts + '</b></span><span>Precisión: <b>' + acc + ' %</b></span><span>PPM: <b>' + ppm + '</b></span><span>Errores: <b>' + ty.err + '</b></span>';
    hintNext();
  }
  function press(ch) {
    if (ty.fin) return;
    if (!ty.t0) ty.t0 = Date.now();
    if (ch === TEXT[ty.pos]) { ty.ok++; ty.pos++; ty.bad = false; flash(ch, 'ok'); } else { ty.err++; ty.bad = true; flash(ch, 'bad'); }
    draw();
    if (ty.pos >= TEXT.length) fin();
  }
  function botones(pass) {
    var h = '<button type="button" id="brep">' + (pass === null ? 'Reiniciar' : 'Repetir') + '</button>';
    if (pass && HAYMAS) h += '<button type="button" id="bsig">Siguiente nivel</button>';
    h += '<button type="button" id="bmenu">Niveles</button>';
    $('#tnav').innerHTML = h;
    $('#brep').onclick = function () { location.href = URL_ESTE; };
    if ($('#bsig')) $('#bsig').onclick = function () { location.href = URL_SIG; };
    $('#bmenu').onclick = function () { location.href = URL_MENU; };
  }
  function fin() {
    ty.fin = true; hintNext();
    $('#tfb').textContent = 'Calculando resultado…';
    var body = new URLSearchParams({ aciertos: ty.ok, errores: ty.err });
    fetch(URL_RES, { method: 'POST', body: body, headers: { 'Content-Type': 'application/x-www-form-urlencoded' } })
      .then(function (r) { return r.json(); })
      .then(function (r) { $('#tfb').textContent = r.mensaje; botones(r.supera); })
      .catch(function () { $('#tfb').textContent = 'No se pudo guardar el resultado. Recarga la página.'; botones(false); });
  }
  addEventListener('keydown', function (e) {
    if (!ty || ty.fin) return;
    if (e.ctrlKey || e.metaKey || e.altKey) return;
    if (e.key === 'Shift') { K.Shift.classList.add('sel'); K.ShiftR.classList.add('sel'); return; }
    if (e.key.length !== 1) return;
    e.preventDefault(); press(e.key);
  });
  addEventListener('keyup', function (e) {
    if (e.key === 'Shift' && K.Shift && !shiftOn) { K.Shift.classList.remove('sel'); K.ShiftR.classList.remove('sel'); }
  });
  ty = { pos: 0, ok: 0, err: 0, t0: null, bad: false, fin: false };
  buildKb(); botones(null); draw();
})();
