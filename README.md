# NLP_graph

This repository is for the Multidimensional Idea Model project, a collection of code and data to implement a hypergraph
of words (vertices) and their relationships (edges). MIM is currently conceived of as "fractal" hypergraph, in the sense 
that the edge-relationships are themselves composed of a set of vertices and edges, so that the way word/relationship data
is encoded in the model is contained within the model itself. Thus, within MIM there are vertex-words and edge-relations
which are conceptually at the "base" level (or perhaps a better term would be "facet" since the model is fractal and the concept
of a level cognitively implies a hierarchy).

From that base, there are two distinctive facets that arise, which are essentially the programmatic logic encoding facet 
and the linguistic data facet. The logic facet represents the way that liguistic data are stored, while the data facet represents
the linguistic data itself (such as parts of speech, word classes, and other semantic and sytactic categories). The categories
within the logic facet are yet to be fully defined, but will include possible terms such as "quiver" (a subset of vertices
and edges) or "morphism" (an abstraction referring to any relation function from one vertex to another). These terms are borrowed
from the website nLab <https://ncatlab.org/nlab/show/HomePage>, and "quiver" appears at <https://ncatlab.org/nlab/show/quiver>,
"morphism" derives from the mathematical concept of "category" at <https://ncatlab.org/nlab/show/category#idea>.
