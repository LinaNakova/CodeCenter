describe('Login Test', () => {
  it('should add tag successfully', () => {
    cy.visit('http://localhost:4200');
    cy.get('#login').click();
    cy.url().should('include', '/login');
    cy.get('input[name="username"]').type('cypress.test');
    cy.get('input[name="password"]').type('cypress');
    cy.get('#login-btn').click();
    cy.url().should('include', '/top-questions');
    cy.get('a[href="/tags"]').click()
    cy.url().should('include', '/tags');
    cy.get('a[href="/addTag"]').click({force: true})
    cy.url().should('include', '/addTag');
    cy.get('#exampleFormControlInput1').type('cypress', {force: true});
    cy.get('#exampleFormControlTextarea1').type('cypress explanation', {force: true});
    cy.get('button[type="submit"]:last').click({force: true});
    cy.visit('http://localhost:4200/tags');
    cy.url().should('include', '/tags');
    cy.get('.card-title').should('contain.text', 'cypress');
    cy.get('.card-text').should('contain.text', 'cypress explanation');
  });
});
