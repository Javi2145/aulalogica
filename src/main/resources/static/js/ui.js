/* Mejoras de interfaz (opcionales): menú lateral, filtro en vivo y confirmaciones.
   Toda la lógica didáctica (validación, cálculos, puntajes) se ejecuta en Java. */
(function () {
  var layout = document.querySelector('.layout');
  var sbt = document.getElementById('sbt');
  if (sbt && layout) {
    sbt.addEventListener('click', function () {
      var c = !layout.classList.contains('collapsed');
      layout.classList.toggle('collapsed', c);
      sbt.textContent = c ? '▶' : '◀';
      sbt.setAttribute('aria-label', c ? 'Mostrar menú' : 'Ocultar menú');
      sbt.setAttribute('aria-expanded', String(!c));
    });
  }
  var abiertas = [];
  try { abiertas = JSON.parse(sessionStorage.getItem('al_open') || '[]'); } catch (e) { abiertas = []; }
  document.querySelectorAll('.sec').forEach(function (sec) {
    var id = sec.id;
    var ex = sec.querySelector('.ex');
    function poner(o) {
      sec.classList.toggle('open', o);
      if (ex) ex.setAttribute('aria-expanded', String(o));
    }
    if (abiertas.indexOf(id) >= 0) poner(true);
    if (ex) ex.addEventListener('click', function () {
      var o = !sec.classList.contains('open');
      poner(o);
      var i = abiertas.indexOf(id);
      if (o && i < 0) abiertas.push(id);
      if (!o && i >= 0) abiertas.splice(i, 1);
      try { sessionStorage.setItem('al_open', JSON.stringify(abiertas)); } catch (e) { }
    });
  });
  function norm(s) { return s.toLowerCase().normalize('NFD').replace(/[\u0300-\u036f]/g, ''); }
  var q = document.getElementById('q');
  var mods = document.getElementById('mods');
  if (q && mods) {
    q.addEventListener('input', function () {
      var f = norm(q.value), n = 0;
      mods.querySelectorAll('[data-t]').forEach(function (c) {
        var ok = c.getAttribute('data-t').indexOf(f) >= 0;
        c.style.display = ok ? '' : 'none';
        if (ok) n++;
      });
      var vacio = document.getElementById('sinTemas');
      if (vacio) vacio.style.display = n ? 'none' : '';
    });
  }
  document.querySelectorAll('form[data-faltan]').forEach(function (f) {
    f.addEventListener('submit', function (e) {
      var n = parseInt(f.getAttribute('data-faltan'), 10);
      if (n > 0 && !confirm('Te faltan ' + n + ' preguntas por responder. ¿Finalizar igual?')) e.preventDefault();
    });
  });
})();
