
function yes = containsVertex (edge, vertex)

  if(edge(1) == vertex || edge(2) == vertex) 
    yes = true;
  else 
    yes = false;
  endif;

endfunction
