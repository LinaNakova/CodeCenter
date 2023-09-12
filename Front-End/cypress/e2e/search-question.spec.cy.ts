describe('Search question test', () => {
  it('should find question from searched', () => {
    cy.visit('http://localhost:4200');
    cy.get('#search-input').type('answer');
    cy.get('.search-icon').click();
    cy.url().should('include', '/questions/tagged/answer');
    cy.get('.card-title').should('contain.text', 'first answer to first question');
  });
});

