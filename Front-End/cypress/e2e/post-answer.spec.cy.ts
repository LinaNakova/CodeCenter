describe('Post answer test', () => {
  it('should post answer', () => {
    cy.visit('http://localhost:4200');
    cy.get('#login').click();
    cy.url().should('include', '/login');
    cy.get('input[name="username"]').type('cypress.test');
    cy.get('input[name="password"]').type('cypress');
    cy.get('#login-btn').click();
    cy.url().should('include', '/top-questions');

    cy.wait(10000);

    cy.visit('http://localhost:4200/questions/1');
    cy.get('.card-title:last').should('contain.text', 'first answer to first question');
    cy.url().should('include', '/questions/1');
    cy.get('#exampleFormControlInput1').type('Testing answer title');
    cy.get('#exampleFormControlTextarea1').type('Testing answer body');
    cy.get('button[type="submit"]:last').click({force:true});
    cy.get('.card-title').should('contain.text', 'Testing answer title');
    cy.get('.card-text').should('contain.text', 'Testing answer body');
  });
});
