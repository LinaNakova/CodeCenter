describe('Loginpage', () => {
  it('should list all questions by tag', () => {
    cy.visit('http://localhost:4200');
    cy.get('a[href="/tags"]').click()
    cy.url().should('include', '/tags');
    cy.get(".card:first").click({force: true})
    cy.url().should('include', '/allQuestionsWithTag');
  });
});
