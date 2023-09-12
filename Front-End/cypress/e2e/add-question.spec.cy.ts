describe('Loginpage', () => {
  it('should view the profile of the currently logged in user', () => {
    cy.visit('http://localhost:4200');
    cy.get('#login').click();
    cy.url().should('include', '/login');
    cy.get('input[name="username"]').type('cypress.test');
    cy.get('input[name="password"]').type('cypress');
    cy.get('#login-btn').click();
    cy.url().should('include', '/top-questions');
    cy.visit('http://localhost:4200/questions');
    cy.get("a[href='/askQuestion']").click({force: true});
    cy.url().should('include', '/askQuestion')
    cy.get('#exampleFormControlInput1').type('cypress.test', {force: true});
    cy.get('#exampleFormControlTextarea1').type('cypress', {force: true});
    cy.get('#search-box').type('kotlin', {force: true});
    cy.get('.tag-link:first').click({force: true})
    cy.get('button[type="submit"]:last').click({force: true});
  });
});
