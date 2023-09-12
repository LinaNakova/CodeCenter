describe('Open question test', () => {
  it('should open question successfully',() => {
    cy.visit('http://localhost:4200/questions');
    cy.get('app-question').click();
    cy.url().should('include', 'question');
  });
});
